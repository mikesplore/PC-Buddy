package com.mike.vendor.deviceControl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.vendor.Command
import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.model.viewmodel.DeviceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceControlScreen(
    device: NetworkDevice,
    onCommandClick: (String) -> Unit,
    navController: NavController
) {
    var selectedCommand by remember { mutableStateOf<Command?>(null) }
    val deviceName by remember { derivedStateOf { device.name } }
    val onlineStatus by remember { derivedStateOf { device.onlineStatus } }
    val deviceViewModel: DeviceViewModel = hiltViewModel()
    val deviceOnlineStatus by deviceViewModel.deviceOnlineStatus.collectAsState()

    LaunchedEffect(Unit) {
        deviceViewModel.getDeviceOnlineStatus(device.macAddress)
    }



    Scaffold(
        topBar = {
            TopAppBarComponent(deviceName, onlineStatus) {
                // Handle back button click
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                "Tap any button to send command to $deviceName",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            //device List
            DeviceList(selectedCommand, onCommandClick)
            //View Apps Button
            ViewAppsBox(device.macAddress) {
                navController.navigate("apps/${device.macAddress}")
            }
        }

        // Confirmation Dialog
        selectedCommand?.let { command ->
            AlertDialog(
                onDismissRequest = { selectedCommand = null },
                title = {
                    Text(if (deviceOnlineStatus == false) "Device Offline" else command.confirmationTitle)
                },
                text = {
                    Text(
                        if (deviceOnlineStatus == false)
                            "$deviceName is currently offline. Please check the connection and try again."
                        else
                            command.confirmationMessage
                    )
                },
                icon = {
                    Icon(
                        imageVector = command.icon,
                        contentDescription = null,
                        tint = if (deviceOnlineStatus == false) MaterialTheme.colorScheme.error else command.color,
                        modifier = Modifier.size(24.dp)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (deviceOnlineStatus == true) {
                                onCommandClick(command.name)
                            }
                            selectedCommand = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!onlineStatus) MaterialTheme.colorScheme.error else command.color
                        )
                    ) {
                        Text(if (deviceOnlineStatus == false) "OK" else "Confirm")
                    }
                },
                dismissButton = {
                    if (deviceOnlineStatus == true) {
                        TextButton(
                            onClick = { selectedCommand = null }
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ViewAppsBox(macAddress: String, onButtonClick: (String) -> Unit) {
    Button(
        onClick = { onButtonClick(macAddress) },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text("View Apps")
    }
}

