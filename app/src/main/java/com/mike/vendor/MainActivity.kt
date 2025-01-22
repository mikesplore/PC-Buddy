package com.mike.vendor

import android.net.nsd.NsdManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.ui.theme.VendorTheme

class MainActivity : ComponentActivity() {
    private lateinit var networkManager: NetworkManager
    private val discoveredDevices = mutableStateListOf<NetworkDevice>()
    private val deviceOnlineStatus = mutableStateMapOf<String, Boolean>()
    private val handler = Handler(Looper.getMainLooper())
    private val refreshInterval = 10000L // 10 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkManager = NetworkManager(getSystemService(NSD_SERVICE) as NsdManager)

        networkManager.setDeviceCallbacks(
            onDiscovered = { device ->
                if (!discoveredDevices.any { it.host == device.host }) {
                    discoveredDevices.add(device)
                    checkDeviceOnlineStatus(device)
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
                    deviceOnlineStatus = deviceOnlineStatus,
                    onSendCommand = { device, command ->
                        networkManager.sendCommand(device, command)
                    },
                    onRefresh = { refreshDeviceList() }
                )
            }
        }
        startPeriodicRefresh()
    }

    private fun startPeriodicRefresh() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                refreshDeviceList()
                handler.postDelayed(this, refreshInterval)
            }
        }, refreshInterval)
    }

    private fun refreshDeviceList() {
        networkManager.stopDiscovery()
        discoveredDevices.clear()
        networkManager.startDiscovery()
    }

    private fun checkDeviceOnlineStatus(device: NetworkDevice) {
        networkManager.isDeviceOnline(device) { isOnline ->
            deviceOnlineStatus[device.name] = isOnline
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        networkManager.stopDiscovery()
    }
}