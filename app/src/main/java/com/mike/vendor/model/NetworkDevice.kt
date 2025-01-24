package com.mike.vendor.model

import android.net.nsd.NsdServiceInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_devices")
data class NetworkDevice(
    @PrimaryKey val macAddress: String,
    val name: String,
    val host: String,
    val port: Int,
    val serviceInfo: NsdServiceInfo,
    val onlineStatus: Boolean
){
    constructor(macAddress: String, name: String, host: String, port: Int, onlineStatus: Boolean) : this(macAddress, name, host, port, NsdServiceInfo(), onlineStatus)
}