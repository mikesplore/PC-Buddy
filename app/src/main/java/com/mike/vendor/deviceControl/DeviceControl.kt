package com.mike.vendor.deviceControl

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.vendor.api.Command
import com.mike.vendor.api.commands
import com.mike.vendor.api.sendCommand
import com.mike.vendor.model.viewmodel.ServerViewModel
import com.mike.vendor.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerControlScreen(
    macAddress: String,
    context: Context,
    navController: NavController
) {
    var selectedCommand by remember { mutableStateOf<Command?>(null) }
    val serverViewModel: ServerViewModel = hiltViewModel()
    val server by serverViewModel.server.collectAsState()
    val onlineStatus by serverViewModel.serverOnlineStatus.collectAsState()

    LaunchedEffect(macAddress) {
        serverViewModel.getServerOnlineStatus(macAddress)
        serverViewModel.getServer(macAddress).also {
            Log.d("ServerControlScreen", "Fetched server: ${it}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(server?.name ?: "Unknown Server", onlineStatus == true) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(CC.primary())
        ) {

            CommandList(
                selectedCommand = selectedCommand,
                onCommandClick = { commandName ->
                    selectedCommand = commands.find { it.name == commandName }
                },
                modifier = Modifier.fillMaxWidth()
            )
            //view PC information
            ViewPCInformation(navController, macAddress)
        }

        // Confirmation Dialog
        selectedCommand?.let { command ->
            AlertDialog(
                onDismissRequest = { selectedCommand = null },
                title = {
                    Text(if (onlineStatus == false) "Server Offline" else command.confirmationTitle)
                },
                text = {
                    Text(
                        if (onlineStatus == false)
                            "${server?.name ?: "The server"} is currently offline. Please check the connection and try again."
                        else
                            command.confirmationMessage
                    )
                },
                icon = {
                    Icon(
                        imageVector = command.icon,
                        contentDescription = null,
                        tint = if (onlineStatus == false) MaterialTheme.colorScheme.error else command.color,
                        modifier = Modifier.size(24.dp)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (onlineStatus == true) {
                                sendCommand(
                                    command.name,
                                    context,
                                    server?.host ?: "",
                                    server?.port ?: 0
                                )
                            }
                            selectedCommand = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (onlineStatus == false) MaterialTheme.colorScheme.error else command.color
                        )
                    ) {
                        Text(if (onlineStatus == false) "OK" else "Confirm")
                    }
                },
                dismissButton = {
                    if (onlineStatus == true) {
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
fun ViewPCInformation(navController: NavController, macAddress: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        Text("View PC Information", style = CC.titleMedium())
        Spacer(modifier = Modifier.height(16.dp))
        NavigationButton(macAddress, "battery", "Battery", navController)
        Spacer(modifier = Modifier.height(8.dp))
        NavigationButton(macAddress, "memoryAndStorage", "Memory and Storage", navController)


    }
}

@Composable
fun NavigationButton(
    macAddress: String,
    path: String,
    text: String,
    navController: NavController

) {
    Button(
        onClick = {
            navController.navigate("$path/$macAddress")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = CC.extra()
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text, style = CC.subtitleSmall())
    }
}