package com.mike.vendor

import android.net.nsd.NsdManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateMapOf
import com.mike.vendor.model.AppEntity
import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.model.dao.DeviceDao
import com.mike.vendor.networkManager.AppManager
import com.mike.vendor.networkManager.DeviceCommunicator
import com.mike.vendor.networkManager.NetworkManager
import com.mike.vendor.ui.theme.VendorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkManager: NetworkManager
    @Inject
    lateinit var appManager: AppManager
    @Inject
    lateinit var deviceCommunicator: DeviceCommunicator
    private val discoveredDevices = CopyOnWriteArrayList<NetworkDevice>()
    private val deviceOnlineStatus = mutableStateMapOf<String, Boolean>()
    private val handler = Handler(Looper.getMainLooper())
    private val refreshInterval = 10000L // 10 seconds
    private val scope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var deviceDao: DeviceDao

    @Inject
    lateinit var appDao: com.mike.vendor.model.dao.AppDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkManager = NetworkManager(getSystemService(NSD_SERVICE) as NsdManager)

        networkManager.setDeviceCallbacks(
            onDiscovered = { device ->
                if (!discoveredDevices.any { it.host == device.host }) {
                    discoveredDevices.add(device)
                    scope.launch {
                        deviceDao.insertDevice(device)
                    }
                }
            },
            onLost = { info ->
                discoveredDevices.removeAll { it.serviceInfo == info }
            }
        )

        setContent {
            enableEdgeToEdge()
            VendorTheme(darkTheme = false) {
                AppNavHost(
                    discoveredDevices = discoveredDevices,
                    onSendCommand = { device, command ->
                        deviceCommunicator.sendCommand(device, command)
                    }
                )
            }
        }

        // Load devices from the database first
        scope.launch {
            deviceDao.getAllDevices().collect { devices ->
                withContext(Dispatchers.Main) {
                    discoveredDevices.addAll(devices)
                    startPeriodicRefresh()
                }
            }
        }
        scope.launch {
            deviceDao.getAllDevices().collect { devices ->
                withContext(Dispatchers.Main) {
                    discoveredDevices.addAll(devices)
                    loadAppsForDevices(devices)
                }
            }
        }
    }

    private fun startPeriodicRefresh() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                refreshDeviceList()
                checkAllDevicesOnlineStatus()
                handler.postDelayed(this, refreshInterval)
            }
        }, refreshInterval)
    }

    private fun refreshDeviceList() {
        networkManager.stopDiscovery()
        discoveredDevices.clear()
        networkManager.startDiscovery()
    }

    private fun checkAllDevicesOnlineStatus() {
        scope.launch {
            deviceDao.getAllDevices().collect { devices ->
                withContext(Dispatchers.Main) {
                    devices.forEach { device ->
                        checkDeviceOnlineStatus(device)
                    }
                }
            }
        }
    }


    private fun loadAppsForDevices(devices: List<NetworkDevice>) {
        devices.forEach { device ->
            appManager.getInstalledApps(device)
        }
    }

    private fun checkDeviceOnlineStatus(device: NetworkDevice) {
        Log.d("NetworkManager", "Checking online status for device: ${device.name}")
        val sanitizedHost = device.host.trim('/', '[', ']')
        deviceCommunicator.isDeviceOnline(device.copy(host = sanitizedHost)) { isOnline ->
            deviceOnlineStatus[device.name] = isOnline
            scope.launch {
                deviceDao.updateDeviceOnlineStatus(device.macAddress, isOnline)
            }
            Log.d(
                "NetworkManager",
                "Device ${device.name} is ${if (isOnline) "online" else "offline"}"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        networkManager.stopDiscovery()
    }
}