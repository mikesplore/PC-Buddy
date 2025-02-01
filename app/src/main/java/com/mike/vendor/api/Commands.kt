package com.mike.vendor.api

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ModeStandby
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.PowerOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material.icons.filled.WifiTetheringOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Command(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val confirmationTitle: String,
    val confirmationMessage: String,
    val execute: (CommandApiService) -> Call<Void>
)

//General commands
val commands = listOf(
    Command("Reboot", "Restart the device", Icons.Default.Refresh, Color(0xFF4CAF50), "Reboot Device",
        "Are you sure you want to restart this device? This will close all applications and restart the system.") { apiService ->
        apiService.restart().also { println("Restarting device") }
    },
    Command("Shutdown", "Turn off the device", Icons.Default.PowerOff, Color(0xFFF44336), "Shutdown Device",
        "Are you sure you want to shut down this device? This will power off the system.") { apiService ->
        apiService.shutdown().also { println("Shutting down device") }
    },
    Command("Sleep", "Put the device into sleep", Icons.Default.NightlightRound, Color(0xFF9C27B0), "Sleep Device",
        "Are you sure you want to put this device into sleep mode? This will pause all activities and reduce power consumption.") { apiService ->
        apiService.sleep().also { println("Putting device to sleep") }
    },
    Command("Hibernate", "Hibernate the device", Icons.Default.ModeStandby, Color(0xFF7C444F), "Hibernate Device",
        "Are you sure you want to hibernate this device? This will save the current state and turn off the system.") { apiService ->
        apiService.hibernate().also { println("Hibernating device") }
    },
    Command("Lock", "Lock the device", Icons.Default.Lock, Color(0xFF2196F3), "Lock Device",
        "Are you sure you want to lock this device? This will require authentication to access the system again.") { apiService ->
        apiService.lock().also { println("Locking device") }
    },
    Command("Sign Out", "Sign out the current user", Icons.AutoMirrored.Default.ExitToApp, Color(0xff48A6A7), "Log out current user",
        "Are you sure you want to log off this device? This will end the current user session.") { apiService ->
        apiService.logoff().also { println("Logging off device") }
    }
)


