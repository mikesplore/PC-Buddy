package com.mike.vendor.specs.battery

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Battery3Bar
import androidx.compose.material.icons.filled.Battery5Bar
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.vendor.api.fetchBatteryInfo
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.viewmodel.BatteryViewModel
import com.mike.vendor.model.viewmodel.ServerViewModel
import java.util.Locale
import com.mike.vendor.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatteryDetailsScreen(macAddress: String, navController: NavController, context: Context) {
    val batteryViewModel: BatteryViewModel = hiltViewModel()
    val serverViewModel: ServerViewModel = hiltViewModel()
    val server by serverViewModel.server.collectAsState(initial = null)
    val batteryDetails by batteryViewModel.batteryDetails.collectAsState(initial = BatteryDetails())
    var isRefreshing by remember { mutableStateOf(false) }

    // Function to refresh data
    fun refreshData() {
        isRefreshing = true
        serverViewModel.getServer(macAddress)
    }

    LaunchedEffect(server, isRefreshing) {
        server?.let {
            Log.d("BatteryDetailsScreen", "Server found: $it")
            fetchBatteryInfo(
                ipAddress = it.host,
                port = it.port,
                onSuccess = { batteryDetails ->
                    batteryViewModel.insertBatteryDetails(batteryDetails.copy(macAddress = it.macAddress))
                    isRefreshing = false
                },
                onFailure = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    isRefreshing = false
                }
            )
            batteryViewModel.getBatteryDetails(it.macAddress)
        }
    }

    LaunchedEffect(Unit) {
        Log.d("BatteryDetailsScreen", "Fetching battery details for $macAddress")
        refreshData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Battery Details for ${server?.name ?: "Unknown"}", style = CC.titleLarge()) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBackIos, contentDescription = "Back", tint = CC.textColor())
                    }
                },
                actions = {
                    IconButton(
                        onClick = { refreshData() },
                        enabled = !isRefreshing
                    ) {
                        if (isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = CC.textColor()
                            )
                        } else {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = CC.textColor())
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CC.secondary(),

                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CC.primary())
        ) {
            batteryDetails?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    BatteryStatusCard(it)
                    Spacer(modifier = Modifier.height(16.dp))
                    GeneralInfoCard(it)
                    Spacer(modifier = Modifier.height(16.dp))
                    BatteryHealthCard(it)
                }
            }.also {
                if (batteryDetails == null && !isRefreshing) {
                    EmptyStateMessage()
                }
            }
        }
    }
}

@Composable
private fun EmptyStateMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.BatteryAlert,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No battery details found",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun BatteryStatusCard(batteryDetails: BatteryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.primary()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)

        ) {
            Row(
                modifier = Modifier

                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Battery Status",
                    style = CC.titleLarge()
                )
                BatteryStatusIndicator(
                    isCharging = batteryDetails.isCharging,
                    remainingCapacity = batteryDetails.remainingCapacity.toInt()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem(
                "Remaining Capacity",
                String.format(
                    Locale.getDefault(),
                    "%.0f%%",
                    batteryDetails.remainingCapacity.toDouble()
                )
            )
            DetailItem(
                "Time Remaining",
                "Approximately ${batteryDetails.timeRemaining / 60} minutes"
            )
            DetailItem("Voltage", "${batteryDetails.voltage} V")
            DetailItem("Temperature", "${batteryDetails.temperature}°C")
            DetailItem(
                "Charging Status",
                if (batteryDetails.isCharging) "Charging" else "Not Charging"
            )
        }
    }
}

@Composable
private fun GeneralInfoCard(batteryDetails: BatteryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.primary()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "General Information",
                style = CC.titleMedium()
            )
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Name", batteryDetails.name)
            DetailItem("MAC Address", batteryDetails.macAddress)
            DetailItem("Serial Number", batteryDetails.serialNumber)
            DetailItem("Manufacture Date", batteryDetails.manufactureDate.toString())
        }
    }
}

@Composable
private fun BatteryHealthCard(batteryDetails: BatteryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.primary()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Battery Health",
                style = CC.titleMedium()
            )
            Spacer(modifier = Modifier.height(16.dp))
            BatteryHealthIndicator(batteryDetails.batteryHealth.toInt())
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Cycle Count", batteryDetails.cycleCount.toString())
            DetailItem("Design Capacity", "${batteryDetails.designCapacity} mAh")
            DetailItem("Full Charge Capacity", "${batteryDetails.fullChargeCapacity} mAh")
            DetailItem("Battery Chemistry", batteryDetails.batteryChemistry)
        }
    }
}

@Composable
private fun BatteryStatusIndicator(isCharging: Boolean, remainingCapacity: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = when {
                isCharging -> Icons.Default.BatteryChargingFull
                remainingCapacity <= 20 -> Icons.Default.BatteryAlert
                remainingCapacity <= 50 -> Icons.Default.Battery3Bar
                remainingCapacity <= 80 -> Icons.Default.Battery5Bar
                else -> Icons.Default.BatteryFull
            },
            contentDescription = null,
            tint = when {
                isCharging -> Color.Green
                remainingCapacity == 100 -> Color.Green
                remainingCapacity <= 20 -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
private fun BatteryHealthIndicator(healthPercentage: Int) {
    val color = when {
        healthPercentage >= 90 -> Color(0xff006400) // Dark Green
        healthPercentage >= 80 -> Color(0xff228B22) // Forest Green
        healthPercentage >= 70 -> Color(0xff32CD32) // Lime Green
        healthPercentage >= 60 -> Color(0xff66CDAA) // Medium Aquamarine
        healthPercentage >= 50 -> Color(0xff8FBC8F) // Dark Sea Green
        healthPercentage >= 40 -> Color(0xffBDB76B) // Dark Khaki
        healthPercentage >= 30 -> Color(0xffDAA520) // Goldenrod
        healthPercentage >= 20 -> Color(0xffB8860B) // Dark Goldenrod
        healthPercentage >= 10 -> Color(0xff8B4513) // Saddle Brown
        else -> MaterialTheme.colorScheme.error
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Battery Health",
                style = CC.subtitleSmall()
            )
            Text(
                text = "$healthPercentage%",
                style = CC.subtitleSmall(),
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { healthPercentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = CC.secondary(),
        )
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = CC.titleSmall()
        )
        Text(
            text = value,
            style = CC.subtitleSmall()
        )
    }
}
