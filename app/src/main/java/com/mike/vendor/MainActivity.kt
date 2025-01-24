package com.mike.vendor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.mike.vendor.ui.theme.VendorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            VendorTheme {
                ShowDevices(this)
            }
        }
    }
}

@Composable
fun ShowDevices(context: Context){
    val devices = remember { mutableStateListOf<DeviceInfo>() }
    LaunchedEffect(Unit) {
        devices.addAll(getConnectedDevices(context))
    }
    LazyColumn {
        itemsIndexed(devices) { index, device ->
            Text(text = "Device $index: IP - ${device.ipAddress}, Host - ${device.hostName}")
        }
    }
}