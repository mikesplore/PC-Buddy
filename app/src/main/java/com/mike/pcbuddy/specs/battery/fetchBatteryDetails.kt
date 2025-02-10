package com.mike.pcbuddy.specs.battery

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.pcbuddy.api.fetchBatteryInfo
import com.mike.pcbuddy.model.dataClasses.BatteryDetails
import com.mike.pcbuddy.model.viewmodel.BatteryViewModel
import com.mike.pcbuddy.model.viewmodel.ServerViewModel
import com.mike.pcbuddy.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatteryDetailsScreen(macAddress: String, navController: NavController, context: Context) {
    val batteryViewModel: BatteryViewModel = hiltViewModel()
    val serverViewModel: ServerViewModel = hiltViewModel()

    val server by serverViewModel.server.collectAsState(initial = null)
    val batteryDetails by batteryViewModel.batteryDetails.collectAsState(initial = null)

    var isRefreshing by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun refreshData() {
        isRefreshing = true
        error = null
        serverViewModel.getServer(macAddress)
    }

    LaunchedEffect(server, isRefreshing) {
        server?.let { server ->
            Log.d("BatteryDetailsScreen", "Server found: $server")
            fetchBatteryInfo(
                ipAddress = server.host,
                port = server.port,
                onSuccess = { fetchedBatteryDetails ->
                    batteryViewModel.insertBatteryDetails(fetchedBatteryDetails.copy(macAddress = server.macAddress))
                    error = null
                    isRefreshing = false
                },
                onFailure = { errorMessage ->
                    val message = if (errorMessage.contains("Failed to connect")) "The device is offline." else errorMessage
                    error = message
                    isRefreshing = false
                }
            )
            batteryViewModel.getBatteryDetails(server.macAddress)
        }
    }

    LaunchedEffect(Unit) {
        Log.d("BatteryDetailsScreen", "Fetching battery details for $macAddress")
        refreshData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Battery Details for ${server?.name ?: "Unknown"}",
                        style = CC.titleMedium()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back",
                            tint = CC.textColor()
                        )
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
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = CC.textColor()
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CC.primary(),
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
            when {
                error != null -> {
                    ErrorState(
                        error = error!!,
                        onRetry = { refreshData() }
                    )
                }
                batteryDetails == null -> {
                    EmptyStateMessage()
                }
                else -> {
                    BatteryDetailsContent(batteryDetails!!)
                }
            }
        }
    }
}

@Composable
private fun BatteryDetailsContent(batteryDetails: BatteryDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Main Battery Status Section with Circular Indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            BatteryCircularIndicator(
                batteryLevel = batteryDetails.remainingCapacity / batteryDetails.fullChargeCapacity.toDouble(),
                isCharging = batteryDetails.isCharging
            )
        }

        // Quick Stats Row
        QuickStatsRow(batteryDetails)

        // Detailed Information
        DetailedInfoSection(batteryDetails)
    }
}

@Composable
private fun BatteryCircularIndicator(batteryLevel: Double, isCharging: Boolean) {
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(160.dp)) {
            // Draw background circle
            drawCircle(
                color = Color.Gray.copy(alpha = 0.2f),
                radius = size.minDimension / 2,
                style = Stroke(width = 16f)
            )

            // Draw battery level arc
            drawArc(
                color = when {
                    isCharging -> Color(0xFF4CAF50)
                    batteryLevel < 0.2 -> Color(0xFFE57373)
                    batteryLevel < 0.5 -> Color(0xFFFFA726)
                    else -> Color(0xFF4CAF50)
                },
                startAngle = -90f,
                sweepAngle = (batteryLevel * 360).toFloat(),
                useCenter = false,
                style = Stroke(width = 16f)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(batteryLevel * 100).toInt()}%",
                style = CC.titleLarge(),
                color = CC.textColor()
            )
            if (isCharging) {
                Text(
                    text = "Charging",
                    style = CC.labelMedium(),
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
private fun QuickStatsRow(batteryDetails: BatteryDetails) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickStatItem(
            label = "Time Left",
            value = "${batteryDetails.timeRemaining / 60}h ${batteryDetails.timeRemaining % 60}m"
        )
        QuickStatItem(
            label = "Temperature",
            value = "${batteryDetails.temperature}°C"
        )
        QuickStatItem(
            label = "Health",
            value = "${batteryDetails.batteryHealth.toInt()}%"
        )
    }
}

@Composable
private fun QuickStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = CC.labelMedium(),
            color = CC.textColor().copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = CC.titleSmall(),
            color = CC.textColor()
        )
    }
}

@Composable
private fun DetailedInfoSection(batteryDetails: BatteryDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Detailed Information",
            style = CC.titleMedium(),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DetailRow("Serial Number", batteryDetails.serialNumber)
        DetailRow("Chemistry", batteryDetails.batteryChemistry)
        DetailRow("Manufacture Date", batteryDetails.manufactureDate)
        DetailRow("Cycle Count", batteryDetails.cycleCount.toString())
        DetailRow("Design Capacity", "${batteryDetails.designCapacity} mAh")
        DetailRow("Full Charge Capacity", "${batteryDetails.fullChargeCapacity} mAh")
        DetailRow("Current Capacity", "${batteryDetails.remainingCapacity.toInt()} mAh")
        DetailRow("Voltage", "${batteryDetails.voltage}V")
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = CC.subtitleMedium(),
            color = CC.textColor().copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = CC.subtitleMedium(),
            color = CC.textColor()
        )
    }
}

@Composable
private fun ErrorState(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error,
            color = Color.Red.copy(alpha = 0.9f),
            style = CC.subtitleMedium(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = CC.secondary()
            )
        ) {
            Text("Retry")
        }
    }
}


