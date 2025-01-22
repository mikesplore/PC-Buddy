package com.mike.vendor.model

import android.net.nsd.NsdServiceInfo

data class NetworkDevice(
    val name: String,
    val host: String,
    val port: Int,
    val serviceInfo: NsdServiceInfo
)