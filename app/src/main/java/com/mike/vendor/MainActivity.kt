package com.mike.vendor

import AppNavHost
import android.net.nsd.NsdManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import com.mike.vendor.ui.theme.VendorTheme

class MainActivity : ComponentActivity() {
    private lateinit var networkManager: NetworkManager
    private val discoveredDevices = mutableStateListOf<NetworkDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkManager = NetworkManager(getSystemService(NSD_SERVICE) as NsdManager)

        networkManager.setDeviceCallbacks(
            onDiscovered = { device ->
                if (!discoveredDevices.any { it.host == device.host }) {
                    discoveredDevices.add(device)
                }
            },
            onLost = { info ->
                discoveredDevices.removeAll { it.serviceInfo == info }
            }
        )

        setContent {
            enableEdgeToEdge()
            VendorTheme {
                AppNavHost(
                    discoveredDevices = discoveredDevices,
                    onSendCommand = { device, command ->
                        networkManager.sendCommand(device, command)
                    }
                )
            }
        }
        networkManager.startDiscovery()
    }
}