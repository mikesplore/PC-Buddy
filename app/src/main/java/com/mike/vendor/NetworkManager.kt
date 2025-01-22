package com.mike.vendor

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.mike.vendor.model.NetworkDevice
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkManager(private val nsdManager: NsdManager) {
    private val client = OkHttpClient()
    private var onDeviceDiscovered: ((NetworkDevice) -> Unit)? = null
    private var onDeviceLost: ((NsdServiceInfo) -> Unit)? = null
    private var discoveryListener: NsdManager.DiscoveryListener? = null

    fun setDeviceCallbacks(
        onDiscovered: (NetworkDevice) -> Unit,
        onLost: (NsdServiceInfo) -> Unit
    ) {
        onDeviceDiscovered = onDiscovered
        onDeviceLost = onLost
    }

    fun startDiscovery() {
        stopDiscovery() // Stop any ongoing discovery before starting a new one

        discoveryListener = object : NsdManager.DiscoveryListener {
            override fun onDiscoveryStarted(serviceType: String) {
                Log.d("NsdManager", "Discovery started")
            }

            override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                nsdManager.resolveService(serviceInfo, resolveListener)
            }

            override fun onServiceLost(serviceInfo: NsdServiceInfo) {
                onDeviceLost?.invoke(serviceInfo)
            }

            override fun onDiscoveryStopped(serviceType: String) {
                Log.d("NsdManager", "Discovery stopped")
            }

            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                stopDiscovery()
                Log.e("NsdManager", "Discovery failed: Error $errorCode")
            }

            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e("NsdManager", "Stop discovery failed: Error $errorCode")
            }
        }

        nsdManager.discoverServices("_pccontrol._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    fun stopDiscovery() {
        discoveryListener?.let {
            nsdManager.stopServiceDiscovery(it)
            discoveryListener = null
        }
    }

    fun sendCommand(device: NetworkDevice, command: String) {
        val request = Request.Builder()
            .url("http://${device.host}:${device.port}/$command")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Network", "Failed to send command", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Network", "Command sent successfully: ${response.code}")
            }
        })
    }

    fun isDeviceOnline(device: NetworkDevice, callback: (Boolean) -> Unit) {
        val request = Request.Builder()
            .url("http://${device.host}:${device.port}/ping")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.isSuccessful)
            }
        })
    }

    private val resolveListener = object : NsdManager.ResolveListener {
        override fun onResolveFailed(info: NsdServiceInfo, errorCode: Int) {
            Log.e("NsdManager", "Resolve failed: Error $errorCode")
        }

        override fun onServiceResolved(info: NsdServiceInfo) {
            val device = NetworkDevice(
                name = info.serviceName,
                host = info.host.hostAddress ?: "",
                port = info.port,
                serviceInfo = info
            )
            onDeviceDiscovered?.invoke(device)
        }
    }
}