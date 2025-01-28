package com.mike.vendor.deviceControl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.vendor.model.viewmodel.ServerViewModel
import com.mike.vendor.ui.theme.CommonComponents as CC

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
                    Text(
                        text = "PC Buddy",
                        style = CC.titleLarge()
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = CC.secondary().copy(0.5f),
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CC.primary())
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + expandVertically()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (availableDevices.isEmpty()) {
                        NoDevicesFound()
                    } else {
                        Column {
                            Text(
                                text = "Available PCs",
                                style = CC.subtitleLarge(),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Ensure the server is active on the device to appear here.",
                                style = CC.subtitleSmall(),
                                color = CC.secondary(),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                itemsIndexed(
                                    availableDevices,
                                    key = { _, device -> device.macAddress }
                                ) { _, device ->
                                    AnimatedVisibility(
                                        visible = true,
                                        enter = fadeIn() + expandVertically()
                                    ) {
                                        DeviceCard(
                                            device = device,
                                            navController = navController
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoDevicesFound() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.HourglassEmpty,
            contentDescription = null,
            tint = CC.primary(),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "No devices found",
            style = CC.titleMedium(),
            color = CC.secondary(),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "New devices will appear here once discovered",
            color = CC.secondary(),
            style = CC.titleSmall()
        )
    }
}

