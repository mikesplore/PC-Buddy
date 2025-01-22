package com.mike.vendor

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
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



    fun setDeviceCallbacks(
        onDiscovered: (NetworkDevice) -> Unit,
        onLost: (NsdServiceInfo) -> Unit
    ) {
        onDeviceDiscovered = onDiscovered
        onDeviceLost = onLost
    }

    fun startDiscovery() {
        val serviceType = "_pccontrol._tcp."

        nsdManager.discoverServices(
            serviceType,
            NsdManager.PROTOCOL_DNS_SD,
            discoveryListener
        )
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

    private val discoveryListener = object : NsdManager.DiscoveryListener {
        override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
            serviceInfo?.let { info ->
                nsdManager.resolveService(info, resolveListener)
            }
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo?) {
            serviceInfo?.let { info ->
                onDeviceLost?.invoke(info)
            }
        }

        override fun onDiscoveryStarted(regType: String?) {
            Log.d("NsdManager", "Discovery started")
        }

        override fun onDiscoveryStopped(serviceType: String?) {
            Log.d("NsdManager", "Discovery stopped")
        }

        override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
            Log.e("NsdManager", "Discovery failed: Error $errorCode")
        }

        override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
            Log.e("NsdManager", "Stop discovery failed: Error $errorCode")
        }
    }



    private val resolveListener = object : NsdManager.ResolveListener {
        override fun onResolveFailed(info: NsdServiceInfo?, errorCode: Int) {
            Log.e("NsdManager", "Resolve failed: Error $errorCode")
        }

        override fun onServiceResolved(info: NsdServiceInfo?) {
            info?.let { resolved ->
                val device = NetworkDevice(
                    name = resolved.serviceName,
                    host = resolved.host.hostAddress ?: "",
                    port = resolved.port,
                    serviceInfo = resolved
                )
                onDeviceDiscovered?.invoke(device)
            }
        }
    }
}