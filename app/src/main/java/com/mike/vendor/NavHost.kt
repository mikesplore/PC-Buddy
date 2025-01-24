package com.mike.vendor

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.vendor.deviceControl.DeviceControlScreen
import com.mike.vendor.model.NetworkDevice

@Composable
fun AppNavHost(
    discoveredDevices: List<NetworkDevice>,
    onSendCommand: (NetworkDevice, String) -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = "home"
    NavHost(navController, startDestination) {
        composable("home") {
            AvailableDevicesScreen(
                navController = navController,
            )
        }

        composable("device/{macAddress}") { backStackEntry ->
            val macAddress = backStackEntry.arguments?.getString("macAddress") ?: return@composable
            DeviceControlScreen(
                device = discoveredDevices.find { it.macAddress == macAddress }
                    ?: return@composable,
                onCommandClick = { command ->
                    val device = discoveredDevices.find { it.macAddress == macAddress }
                    device?.let { onSendCommand(it, command) }
                },
                navController = navController
            )
        }
        composable("apps/{macAddress}") { backStackEntry ->
            val macAddress = backStackEntry.arguments?.getString("macAddress") ?: return@composable
            AvailableAppsScreen(
                macAddress = macAddress,
            )
        }
    }
}