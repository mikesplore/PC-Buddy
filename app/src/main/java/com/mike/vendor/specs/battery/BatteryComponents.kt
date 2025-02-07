package com.mike.vendor.specs.battery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Battery3Bar
import androidx.compose.material.icons.filled.Battery5Bar
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.ui.theme.CommonComponents
import java.util.Locale

@Composable
 fun BatteryStatusCard(batteryDetails: BatteryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CommonComponents.secondary()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)

        ) {
            Row(
                modifier = Modifier

                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BatteryStatusIndicator(
                    isCharging = batteryDetails.isCharging,
                    remainingCapacity = batteryDetails.remainingCapacity.toInt()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem(
                "Remaining Capacity",
                String.format(
                    Locale.getDefault(),
                    "%.0f%%",
                    batteryDetails.remainingCapacity.toDouble()
                )
            )
            DetailItem(
                "Time Remaining",
                "Approximately ${batteryDetails.timeRemaining / 60} minutes"
            )
            DetailItem("Voltage", "${batteryDetails.voltage} V")
            DetailItem("Temperature", "${batteryDetails.temperature}°C")
            DetailItem(
                "Charging Status",
                if (batteryDetails.isCharging) "Charging" else "Not Charging"
            )
        }
    }
}


@Composable
 fun EmptyStateMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.BatteryAlert,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No battery details found",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun GeneralInfoCard(batteryDetails: BatteryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CommonComponents.secondary()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            DetailItem("Name", batteryDetails.name)
            DetailItem("MAC Address", batteryDetails.macAddress)
            DetailItem("Serial Number", batteryDetails.serialNumber)
            DetailItem("Manufacture Date", batteryDetails.manufactureDate.toString())
        }
    }
}

@Composable
fun BatteryHealthCard(batteryDetails: BatteryDetails) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CommonComponents.secondary()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            BatteryHealthIndicator(batteryDetails.batteryHealth.toInt())
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Cycle Count", batteryDetails.cycleCount.toString())
            DetailItem("Design Capacity", "${batteryDetails.designCapacity} mAh")
            DetailItem("Full Charge Capacity", "${batteryDetails.fullChargeCapacity} mAh")
            DetailItem("Battery Chemistry", batteryDetails.batteryChemistry)
        }
    }
}

@Composable
fun BatteryStatusIndicator(isCharging: Boolean, remainingCapacity: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = when {
                isCharging -> Icons.Default.BatteryChargingFull
                remainingCapacity <= 20 -> Icons.Default.BatteryAlert
                remainingCapacity <= 50 -> Icons.Default.Battery3Bar
                remainingCapacity <= 80 -> Icons.Default.Battery5Bar
                else -> Icons.Default.BatteryFull
            },
            contentDescription = null,
            tint = when {
                isCharging -> Color.Green
                remainingCapacity == 100 -> Color.Green
                remainingCapacity >= 80 -> Color(0xFF32CD32) // Lime Green
                remainingCapacity >= 60 -> Color(0xFF66CDAA) // Medium Aquamarine
                remainingCapacity >= 40 -> Color(0xFFBDB76B) // Dark Khaki
                remainingCapacity >= 20 -> CommonComponents.tertiary()
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
fun BatteryHealthIndicator(healthPercentage: Int) {
    val color = when {
        healthPercentage >= 90 -> Color(0xff006400) // Dark Green
        healthPercentage >= 80 -> Color(0xff228B22) // Forest Green
        healthPercentage >= 70 -> Color(0xff32CD32) // Lime Green
        healthPercentage >= 60 -> Color(0xff66CDAA) // Medium Aquamarine
        healthPercentage >= 50 -> Color(0xff8FBC8F) // Dark Sea Green
        healthPercentage >= 40 -> Color(0xffBDB76B) // Dark Khaki
        healthPercentage >= 30 -> Color(0xffDAA520) // Goldenrod
        healthPercentage >= 20 -> Color(0xffB8860B) // Dark Goldenrod
        healthPercentage >= 10 -> Color(0xff8B4513) // Saddle Brown
        else -> MaterialTheme.colorScheme.error
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Battery Health",
                style = CommonComponents.subtitleSmall()
            )
            Text(
                text = "$healthPercentage%",
                style = CommonComponents.subtitleSmall(),
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { healthPercentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = CommonComponents.tertiary(),
        )
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = CommonComponents.titleSmall()
        )
        Text(
            text = value,
            style = CommonComponents.subtitleSmall()
        )
    }
}
