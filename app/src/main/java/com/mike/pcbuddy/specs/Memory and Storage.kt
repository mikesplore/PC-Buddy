package com.mike.pcbuddy.specs

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.pcbuddy.api.fetchMemoryInfo
import com.mike.pcbuddy.api.fetchStorageInfo
import com.mike.pcbuddy.model.dataClasses.MemoryDetails
import com.mike.pcbuddy.model.dataClasses.MountPointDetails
import com.mike.pcbuddy.model.dataClasses.PieChartData
import com.mike.pcbuddy.model.dataClasses.PieChartWithLegend
import com.mike.pcbuddy.model.dataClasses.StorageInfo
import com.mike.pcbuddy.model.viewmodel.MemoryAndStorageViewModel
import com.mike.pcbuddy.model.viewmodel.ServerViewModel
import java.util.Locale
import com.mike.pcbuddy.ui.theme.CommonComponents as CC


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryAndStorageDetails(
    macAddress: String,
    navController: NavController,
    context: Context
) {
    val viewModel: MemoryAndStorageViewModel = hiltViewModel()
    val serverViewModel: ServerViewModel = hiltViewModel()
    val server by serverViewModel.server.collectAsState(initial = null)
    val memoryDetails by viewModel.memoryDetails.collectAsState(initial = null)
    val storageInfo by viewModel.storageInfo.collectAsState(initial = null)
    var isRefreshing by remember { mutableStateOf(false) }
    var serverMac by remember { mutableStateOf("") }

    fun refreshData() {
        isRefreshing = true
        serverViewModel.getServer(macAddress)
    }

    LaunchedEffect(server, isRefreshing) {
        server?.let {
            serverMac = it.macAddress
            viewModel.getStorageInfo(serverMac)
            viewModel.getMemoryDetails(serverMac)
            if (isRefreshing) {
                fetchMemoryInfo(
                    ipAddress = it.host,
                    port = it.port,
                    onSuccess = {
                        viewModel.insertMemoryDetails(it.copy(macAddress = serverMac))
                        isRefreshing = false
                    },
                    onFailure = {
                        isRefreshing = false
                        Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                    }
                )

                fetchStorageInfo(
                    ipAddress = it.host,
                    port = it.port,
                    onSuccess = {
                        viewModel.insertStorageInfo(it.copy(macAddress = serverMac))
                        isRefreshing = false
                    },
                    onFailure = {
                        isRefreshing = false
                        Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        refreshData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "System Details: ${server?.name ?: "Unknown"}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CC.titleMedium(),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBackIos, "Back", tint = CC.textColor())
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
                                color = CC.textColor(),
                                strokeWidth = 1.dp
                            )
                        } else {
                            Icon(Icons.Default.Refresh, "Refresh", tint = CC.primary())
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CC.primary()
                )
            )
        }
    ) { padding ->
        SystemDetailsContent(
            memoryDetails = memoryDetails,
            storageInfo = storageInfo,
            modifier = Modifier.padding(padding)
        )

    }
}

@Composable
private fun SystemDetailsContent(
    memoryDetails: MemoryDetails?,
    storageInfo: StorageInfo?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CC.primary())
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Memory Section
        memoryDetails?.let { memory ->
            MemoryCard(memory)
        }

        // Storage Section
        storageInfo?.let { storage ->
            StorageCard(storage)
        }
    }
}

@Composable
private fun MemoryCard(memoryDetails: MemoryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.primary()
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Memory Usage",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Memory Usage Chart
            PieChartWithLegend(
                chartData = listOf(
                    PieChartData("Used", memoryDetails.used.toFloat(), CC.textColor()),
                    PieChartData(
                        "Cache",
                        memoryDetails.cacheSize.toFloat(),
                        MaterialTheme.colorScheme.tertiary
                    ),
                    PieChartData("Available", memoryDetails.available.toFloat(), CC.secondary())
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Memory Details
            MemoryDetailRow("Total Memory", formatBytes(memoryDetails.total))
            MemoryDetailRow("Used Memory", formatBytes(memoryDetails.used))
            MemoryDetailRow("Available Memory", formatBytes(memoryDetails.available))
            MemoryDetailRow("Cache Size", formatBytes(memoryDetails.cacheSize))

            if (memoryDetails.swapTotal > 0) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = CC.tertiary())
                Text("Swap Memory", style = CC.titleSmall())
                MemoryDetailRow("Total Swap", formatBytes(memoryDetails.swapTotal))
                MemoryDetailRow("Used Swap", formatBytes(memoryDetails.swapUsed))
            }
        }
    }
}

@Composable
private fun StorageCard(storageInfo: StorageInfo) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.primary()
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Storage Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Overall Storage Chart
            PieChartWithLegend(
                chartData = listOf(
                    PieChartData("Used", storageInfo.usedSpace.toFloat(), CC.textColor()),
                    PieChartData("Free", storageInfo.freeSpace.toFloat(), CC.secondary())
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            StorageDetailRow("Total Space", formatBytes(storageInfo.totalSpace))
            StorageDetailRow("Used Space", formatBytes(storageInfo.usedSpace))
            StorageDetailRow("Free Space", formatBytes(storageInfo.freeSpace))

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = CC.tertiary())

            Text(
                text = "Mount Points",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            storageInfo.mountPoints.forEach { mountPoint ->
                MountPointItem(mountPoint)
            }
        }
    }
}

@Composable
private fun MountPointItem(mountPoint: MountPointDetails) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.primary()
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = mountPoint.description,
                    style = CC.subtitleSmall()
                )

                Text(
                    text = mountPoint.type,
                    style = CC.subtitleSmall()
                )
            }

            LinearProgressIndicator(
                progress = { (mountPoint.usagePercentage / 100f).toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    mountPoint.usagePercentage > 90 -> MaterialTheme.colorScheme.error
                    mountPoint.usagePercentage > 75 -> Color.Red
                    mountPoint.usagePercentage > 60 -> Color(0xFFFFA500)
                    mountPoint.usagePercentage > 45 -> Color.Yellow
                    mountPoint.usagePercentage > 30 -> Color.Green
                    mountPoint.usagePercentage > 15 -> Color.Cyan
                    else -> MaterialTheme.colorScheme.primary
                },
                trackColor = CC.secondary(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Used: ${formatBytes(mountPoint.used)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${String.format(Locale.getDefault(), "%d", mountPoint.usagePercentage.toInt())}% of ${formatBytes(mountPoint.total)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Free: ${formatBytes(mountPoint.available)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun MemoryDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StorageDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun formatBytes(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var value = bytes.toDouble()
    var unitIndex = 0

    while (value >= 1024 && unitIndex < units.size - 1) {
        value /= 1024
        unitIndex++
    }

    return "%.2f %s".format(value, units[unitIndex])
}

