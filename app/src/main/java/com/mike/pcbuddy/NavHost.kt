package com.mike.pcbuddy

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.pcbuddy.deviceControl.AvailableDevicesScreen
import com.mike.pcbuddy.deviceControl.ServerControlScreen
import com.mike.pcbuddy.specs.battery.BatteryDetailsScreen
import com.mike.pcbuddy.specs.DisplayInfoScreen
import com.mike.pcbuddy.specs.MemoryAndStorageDetails
import com.mike.pcbuddy.specs.SystemInfoScreen
import com.mike.pcbuddy.specs.ViewPCInformation

@Composable
fun AppNavHost(
    context: Context
) {
    val navController = rememberNavController()

    NavHost(startDestination = "home", navController = navController) {
        composable("home") {
            AvailableDevicesScreen(navController)
        }

        composable("device/{macAddress}") { backStackEntry ->
            ServerControlScreen(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",
                context = context,
                navController = navController
            )
        }

        composable("battery/{macAddress}") { backStackEntry ->
            BatteryDetailsScreen(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",
                navController = navController,
                context = context
            )
        }

        composable("memoryAndStorage/{macAddress}") { backStackEntry ->
            MemoryAndStorageDetails(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",
                navController = navController,
                context = context
            )
        }

        composable("display/{macAddress}") { backStackEntry ->
            DisplayInfoScreen(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",
                context = context
            )
        }

        composable("pcInfo/{macAddress}") { backStackEntry ->
            ViewPCInformation(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",
                navController = navController,
            )
        }

        composable("systemInfo/{macAddress}") { backStackEntry ->
            SystemInfoScreen(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",

            )
        }
    }
}