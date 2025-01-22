package com.mike.vendor.model

import android.net.nsd.NsdServiceInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_devices")
data class NetworkDevice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val host: String,
    val port: Int,
    val serviceInfo: NsdServiceInfo
){
    constructor(name: String, host: String, port: Int, serviceInfo: NsdServiceInfo) : this(0, name, host, port, serviceInfo)
}