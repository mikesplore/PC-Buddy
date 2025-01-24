package com.mike.vendor

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Power
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Command(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val description: String,
    val confirmationTitle: String,
    var confirmationMessage: String
)

val commands = listOf(
    Command(
        name = "shutdown",
        icon = Icons.Default.Power,
        color = Color(0xFFE53935),
        description = "Power off device",
        confirmationTitle = "Shutdown Device",
        confirmationMessage = "This will immediately shut down the device. Make sure all your work is saved. Do you want to continue?"
    ),
    Command(
        name = "restart",
        icon = Icons.Default.Refresh,
        color = Color(0xFF43A047),
        description = "Restart device",
        confirmationTitle = "Restart Device",
        confirmationMessage = "This will restart the device. All unsaved work will be lost. Do you want to continue?"
    ),
    Command(
        name = "sleep",
        icon = Icons.Default.NightsStay,
        color = Color(0xFF1E88E5),
        description = "Put device to sleep",
        confirmationTitle = "Sleep Mode",
        confirmationMessage = "This will put the device into sleep mode. Current tasks will be suspended. Do you want to continue?"
    ),
    Command(
        name = "lock",
        icon = Icons.Default.Lock,
        color = Color(0XFFEFB036),
        description = "Lock device screen",
        confirmationTitle = "Lock Screen",
        confirmationMessage = "This will lock the device screen. You'll need to enter the password to unlock it. Do you want to continue?"
    )
)
