package com.mike.vendor

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.NetworkInterface

data class DeviceInfo(val ipAddress: String, val hostName: String)

suspend fun getConnectedDevices(context: Context): List<DeviceInfo> = withContext(Dispatchers.IO) {
    val connectedDevices = mutableListOf<DeviceInfo>()
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val dhcpInfo = wifiManager.dhcpInfo // 'val dhcpInfo: DhcpInfo!' is deprecated. Deprecated in Java.
    val subnet = dhcpInfo.ipAddress and dhcpInfo.netmask

    for (i in 1..254) {
        val ip = subnet or i
        val address = InetAddress.getByAddress(
            byteArrayOf(
                (ip shr 24 and 0xFF).toByte(),
                (ip shr 16 and 0xFF).toByte(),
                (ip shr 8 and 0xFF).toByte(),
                (ip and 0xFF).toByte()
            )
        )
        if (address.isReachable(1000)) {
            val networkInterface = NetworkInterface.getByInetAddress(address)
            if (networkInterface != null) {
                val hostName = address.hostName
                val ipAddress = address.hostAddress ?: ""
                connectedDevices.add(DeviceInfo(ipAddress, hostName))
                Log.d("Connected Device", "IP: $ipAddress, Host: $hostName")
            }
        }
    }
    connectedDevices
}