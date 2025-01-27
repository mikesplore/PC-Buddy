package com.mike.vendor

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.vendor.deviceControl.ServerControlScreen

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
    }
}