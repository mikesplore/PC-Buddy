package com.mike.vendor.specs

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.vendor.model.viewmodel.ServerViewModel
import com.mike.vendor.model.viewmodel.SystemInfoViewModel
import java.text.DateFormat
import java.util.Date
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.mike.vendor.api.fetchSystemInfo
import com.mike.vendor.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemInfoScreen(
    macAddress: String,
    navController: NavController,
    context: Context
) {
    val serverViewModel: ServerViewModel = hiltViewModel()
    val systemInfoViewModel: SystemInfoViewModel = hiltViewModel()

    val server by serverViewModel.server.collectAsState()
    val processorDetails by systemInfoViewModel.processorDetails.collectAsState()
    val computerSystem by systemInfoViewModel.computerSystemDetails.collectAsState()
    val osInfo by systemInfoViewModel.operatingSystemInfo.collectAsState()
    val softwareInfo by systemInfoViewModel.softwareInfo.collectAsState()
    val userInfo by systemInfoViewModel.userEnvironmentInfo.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    fun refreshData() {
        isRefreshing = true
        serverViewModel.getServer(macAddress)
    }

    LaunchedEffect(server, isRefreshing) {
        server?.let { serv ->
            serverViewModel.getServer(macAddress)

            fetchSystemInfo(
                ipAddress = serv.host,
                port = serv.port,
                onSuccess = { systemInfo ->
                    systemInfoViewModel.insertAllInfo(
                        processorDetails = systemInfo.processorDetails?.copy(macAddress = macAddress),
                        computerSystemDetails = systemInfo.computerSystemDetails?.copy(macAddress = macAddress),
                        operatingSystemInfo = systemInfo.operatingSystemInfo?.copy(macAddress = macAddress),
                        softwareInfo = systemInfo.softwareInfo?.copy(macAddress = macAddress),
                        userEnvironmentInfo = systemInfo.userEnvironmentInfo?.copy(macAddress = macAddress)
                    )
                    systemInfoViewModel.getSystemInfo(macAddress)
                    isRefreshing = false
                },
                onFailure = {
                    // Handle error
                    isRefreshing = false

                }
            )
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
                        text = "System Details",
                        style = CC.titleLarge()
                    )
                },
                actions = {
                    IconButton(onClick = {isRefreshing= true}) {
                        if(isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = CC.textColor(),
                                strokeWidth = 1.dp
                            )
                        }
                        else{
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = CC.textColor()
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CC.extra()
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .background(CC.primary())
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Processor Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                DetailSection(
                    title = "Processor",
                    icon = Icons.Default.Memory,
                ) {
                    processorDetails?.let {
                        Text(it.name,
                            style = CC.titleSmall(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                            ) }
                    DetailRow("Physical Cores", processorDetails?.physicalCoreCount.toString())
                    DetailRow("Logical Cores", processorDetails?.logicalCoreCount.toString())
                    DetailRow("Max Frequency", "${(processorDetails?.maxFrequency ?: 0) / 1000000} MHz")

                    // CPU Frequencies Chart
                    Text(
                        "Core Frequencies",
                        style = CC.titleSmall(),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    processorDetails?.currentFrequency?.let { CoreFrequenciesChart(it) }
                }
            }

            // Computer System Section
            item {
                DetailSection(
                    title = "System",
                    icon = Icons.Default.Computer
                ) {
                    computerSystem?.let { DetailRow("Model", it.model) }
                    computerSystem?.let { DetailRow("Manufacturer", it.manufacturer) }
                    computerSystem?.let { DetailRow("Serial Number", it.serialNumber) }
                }
            }

            // Operating System Section
            item {
                DetailSection(
                    title = "Operating System",
                    icon = Icons.Default.Settings
                ) {
                    osInfo?.let { os ->
                        DetailRow("Name", os.name)
                        DetailRow("Version", os.version)
                        DetailRow("Build", os.buildNumber)
                        DetailRow("Architecture", os.architecture)
                        DetailRow("Manufacturer", os.manufacturer)
                        DetailRow("Install Date", os.installDate?.let {
                            DateFormat.getDateInstance().format(Date(it * 1000))
                        })
                    }
                }
            }

            // Software Info Section
            item {
                DetailSection(
                    title = "Software Status",
                    icon = Icons.Default.Apps
                ) {
                    DetailRow("Running Processes", softwareInfo?.runningProcessCount.toString())
                    softwareInfo?.let { formatUptime(it.systemUptime) }?.let {
                        DetailRow(
                            "System Uptime",
                            it
                        )
                    }
                }
            }

            // User Environment Section
            item {
                DetailSection(
                    title = "User Environment",
                    icon = Icons.Default.Person,
                ) {
                    userInfo?.let {  user ->
                    DetailRow("Username", user.username)
                    DetailRow("Home Directory", user.homeDirectory)
                    DetailRow("Timezone", user.timezone)
                    DetailRow("Locale", user.locale.language)
                    DetailRow("Country", user.country)
                    DetailRow("Language", user.language)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    icon: ImageVector,
    background: Color = Color(0xff9BB8CD),
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = background.copy(0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = CC.textColor(),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = CC.titleMedium()
                )
            }
            content()
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String?
) {
    value?.let {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = CC.subtitleMedium(),
                color = Color(0xff316B83)
            )
            Text(
                text = it,
                style = CC.subtitleMedium()
            )
        }
    }
}


@Composable
private fun CoreFrequenciesChart(frequencies: LongArray) {
    val maxFrequency = frequencies.maxOrNull() ?: 1L
    val levelThreshold = maxFrequency / 5

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CC.extra().copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                frequencies.forEachIndexed { index, freq ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${freq / 1000000} MHz",
                            style = CC.subtitleSmall(),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        // Levels representation
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp) // Fixed height for levels visualization
                                .background(Color(0xff316B83).copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                repeat(5) { level ->
                                    val color = when {
                                        freq >= (level + 1) * levelThreshold -> CC.secondary()
                                        else -> CC.extra().copy(alpha = 0.3f)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .background(
                                                color,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Core ${index + 1}",
                            style = CC.subtitleSmall(),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}


private fun formatUptime(uptimeSeconds: Long): String {
    val seconds = uptimeSeconds % 60
    val minutes = (uptimeSeconds / 60) % 60
    val hours = (uptimeSeconds / 3600) % 24
    val days = uptimeSeconds / (3600 * 24)

    return buildString {
        if (days > 0) append("${days}d ")
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m ")
        append("${seconds}s")
    }
}

