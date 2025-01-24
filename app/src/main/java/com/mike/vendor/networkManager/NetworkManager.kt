package com.mike.vendor.networkManager

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.mike.vendor.model.NetworkDevice
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RateLimiter(private val maxRequests: Int, private val timeWindow: Long, private val timeUnit: TimeUnit) {
    private val requestTimestamps = mutableListOf<Long>()

    @Synchronized
    fun shouldAllowRequest(): Boolean {
        val currentTime = System.currentTimeMillis()
        val windowStart = currentTime - timeUnit.toMillis(timeWindow)

        // Remove timestamps outside the time window
        requestTimestamps.removeAll { it < windowStart }

        return if (requestTimestamps.size < maxRequests) {
            requestTimestamps.add(currentTime)
            true
        } else {
            false
        }
    }
}

class NetworkManager @Inject constructor(private val nsdManager: NsdManager) {

    private var onDeviceDiscovered: ((NetworkDevice) -> Unit)? = null
    private var onDeviceLost: ((NsdServiceInfo) -> Unit)? = null
    private var discoveryListener: NsdManager.DiscoveryListener? = null
    private val rateLimiter = RateLimiter(maxRequests = 10, timeWindow = 1, timeUnit = TimeUnit.MINUTES)

    fun setDeviceCallbacks(
        onDiscovered: (NetworkDevice) -> Unit,
        onLost: (NsdServiceInfo) -> Unit
    ) {
        onDeviceDiscovered = onDiscovered
        onDeviceLost = onLost
    }

    fun startDiscovery() {
        if (rateLimiter.shouldAllowRequest()) {
            stopDiscovery()

            discoveryListener = object : NsdManager.DiscoveryListener {
                override fun onDiscoveryStarted(serviceType: String) {
                    Log.d("NsdManager", "Discovery started")
                }

                override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                    resolveService(serviceInfo)
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

            nsdManager.discoverServices(
                "_pccontrol._tcp.",
                NsdManager.PROTOCOL_DNS_SD,
                discoveryListener
            )
        } else {
            Log.w("NetworkManager", "Too many requests, skipping discovery")
        }
    }

    fun stopDiscovery() {
        discoveryListener?.let {
            nsdManager.stopServiceDiscovery(it)
            discoveryListener = null
        }
    }

    private fun resolveService(service: NsdServiceInfo) {
        val resolveListener = object : NsdManager.ResolveListener {
            override fun onResolveFailed(info: NsdServiceInfo, errorCode: Int) {
                Log.e("NsdManager", "Resolve failed: Error $errorCode")
            }

            @RequiresExtension(extension = Build.VERSION_CODES.TIRAMISU, version = 7)
            override fun onServiceResolved(info: NsdServiceInfo) {
                val device = NetworkDevice(
                    macAddress = getMacAddress(info),
                    name = info.serviceName,
                    host = info.hostAddresses.toString().trim('/', '[', ']'),
                    port = info.port,
                    serviceInfo = info,
                    onlineStatus = false
                )
                onDeviceDiscovered?.invoke(device)
            }
        }
        nsdManager.resolveService(service, resolveListener)
    }

    private fun getMacAddress(info: NsdServiceInfo): String {
        val txtRecord = info.attributes
        val macBytes = txtRecord["mac"] ?: return ""
        return macBytes.joinToString(":") { byte -> String.format("%02X", byte) }
    }
}