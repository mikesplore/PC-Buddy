package com.mike.vendor

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.vendor.deviceControl.AvailableDevicesScreen
import com.mike.vendor.deviceControl.ServerControlScreen
import com.mike.vendor.specs.BatteryDetailsScreen
import com.mike.vendor.specs.DisplayInfoScreen
import com.mike.vendor.specs.MemoryAndStorageDetails
import com.mike.vendor.specs.ViewPCInformation

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
                navController = navController,
                context = context
            )
        }

        composable("pcInfo/{macAddress}") { backStackEntry ->
            ViewPCInformation(
                macAddress = backStackEntry.arguments?.getString("macAddress") ?: "",
                navController = navController,
            )
        }
    }
}