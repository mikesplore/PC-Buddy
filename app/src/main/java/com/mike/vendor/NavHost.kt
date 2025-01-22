package com.mike.vendor

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.vendor.model.NetworkDevice

@Composable
fun AppNavHost(
    discoveredDevices: List<NetworkDevice>,
    onSendCommand: (NetworkDevice, String) -> Unit,
    deviceOnlineStatus: Map<String, Boolean>,
    onRefresh:() -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = "home"
    NavHost(navController, startDestination) {
        composable("home") {
            AvailableDevicesScreen(
                discoveredDevices = discoveredDevices,
                navController = navController,
                deviceOnlineStatus = deviceOnlineStatus,
                onRefresh = onRefresh
            )
        }

        composable("device/{deviceName}") { backStackEntry ->
            val deviceName = backStackEntry.arguments?.getString("deviceName") ?: return@composable
            DeviceControlScreen(
                deviceName = deviceName,
                onCommandClick = { command ->
                    val device = discoveredDevices.find { it.name == deviceName }
                    device?.let { onSendCommand(it, command) }
                },
                onlineStatus = deviceOnlineStatus[deviceName] ?: false
            )
        }
    }
}