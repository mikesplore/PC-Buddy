package com.mike.vendor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceControlScreen(
    deviceName: String,
    onlineStatus: Boolean,
    onCommandClick: (String) -> Unit
) {
    var selectedCommand by remember { mutableStateOf<Command?>(null) }
    var showOfflineDevice by remember { mutableStateOf(!onlineStatus) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        deviceName,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Control Center",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                "Tap any button to control $deviceName",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(commands) { command ->
                    ElevatedCard(
                        onClick = { selectedCommand = command },
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = command.icon,
                                contentDescription = command.name,
                                tint = command.color,
                                modifier = Modifier.size(32.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = command.name.capitalize(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = command.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Confirmation Dialog
    selectedCommand?.let { command ->
        AlertDialog(
            onDismissRequest = { selectedCommand = null },
            title = {
                Text(command.confirmationTitle)
            },
            text = {
                Text(command.confirmationMessage)
            },
            icon = {
                Icon(
                    imageVector = command.icon,
                    contentDescription = null,
                    tint = command.color,
                    modifier = Modifier.size(24.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (!onlineStatus){
                            showOfflineDevice = true
                            return@Button
                        }
                        onCommandClick(command.name)
                        selectedCommand = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = command.color
                    )
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { selectedCommand = null }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

//Defice offline Alert
@Composable
fun OfflineDevice(deviceName: String,dismissRequest: () -> Unit){
    Dialog(onDismissRequest = dismissRequest) {
        Text("Device $deviceName is offline")
    }
}