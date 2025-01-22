package com.mike.vendor.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
 fun DeviceControlScreen(
    deviceName: String,
    commands: List<String>,
    onCommandClick: (String) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Commands for $deviceName",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            commands.forEach { command ->
                Button(
                    onClick = { onCommandClick(command) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(command.capitalize())
                }
            }
        }
    }
}