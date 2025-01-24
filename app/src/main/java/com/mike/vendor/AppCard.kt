package com.mike.vendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.networkManager.AppManager

@Composable
fun AppItemRow(
    app: String,
    device: NetworkDevice,
    appManager: AppManager
) {
    var isOpening by remember { mutableStateOf(false) }
    var isClosing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(app, style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    isOpening = true
                    appManager.openApp(device, app) { success ->
                        isOpening = false
                    }
                },
                enabled = !isOpening && !isClosing
            ) {
                Text("Open")
            }

            Button(
                onClick = {
                    isClosing = true
                    appManager.closeApp(device, app) { success ->
                        isClosing = false
                    }
                },
                enabled = !isOpening && !isClosing,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Close")
            }
        }
    }
}