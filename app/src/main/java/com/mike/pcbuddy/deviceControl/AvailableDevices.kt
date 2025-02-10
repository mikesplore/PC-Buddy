package com.mike.pcbuddy.deviceControl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.pcbuddy.model.Server
import com.mike.pcbuddy.model.viewmodel.ServerViewModel
import com.mike.pcbuddy.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableDevicesScreen(
    navController: NavController
) {
    val serverViewModel: ServerViewModel = hiltViewModel()
    val availableDevices by serverViewModel.servers.collectAsState()


    LaunchedEffect(Unit) {
        serverViewModel.getAllServers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Computer,
                            contentDescription = null,
                            tint = CC.textColor()
                        )
                        Text(
                            text = "PC Buddy",
                            style = CC.titleLarge(),
                            fontFamily = FontFamily.Cursive,
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = CC.primary(),
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CC.primary())
                .padding(padding)
        ) {
            val onlineDevices = availableDevices.filter { it.onlineStatus }
            val offlineDevices = availableDevices.filter { !it.onlineStatus }


            if (availableDevices.isEmpty()) {
                NoDevicesFound()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("The List refreshes every 10 seconds", style = CC.subtitleSmall().copy(color = CC.textColor().copy(alpha = 0.5f)))

                    if (onlineDevices.isNotEmpty()) {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInVertically(animationSpec = tween(500)) + fadeIn(),
                            exit = slideOutVertically(animationSpec = tween(500)) + fadeOut()
                        ) {
                            DeviceSection(
                                title = "Online PCs",
                                devices = onlineDevices,
                                navController = navController
                            )
                        }
                    }

                    if (offlineDevices.isNotEmpty()) {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInVertically(animationSpec = tween(500)) + fadeIn(),
                            exit = slideOutVertically(animationSpec = tween(500)) + fadeOut()
                        ) {
                            DeviceSection(
                                title = "Offline PCs",
                                devices = offlineDevices,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DeviceSection(
    title: String,
    devices: List<Server>,
    navController: NavController
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = CC.titleSmall()
                )
            }

        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                devices,
                key = { _, device -> device.macAddress }
            ) { _, device ->
                DeviceCard(
                    device = device,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun NoDevicesFound() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CC.extra().copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.HourglassEmpty,
                    contentDescription = null,
                    tint = CC.textColor(),
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "No devices detected",
                    style = CC.titleMedium(),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Online or previously connected devices will appear here.",
                    style = CC.subtitleSmall(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


