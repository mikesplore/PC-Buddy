package com.mike.vendor.model

import androidx.room.TypeConverter
import android.net.nsd.NsdServiceInfo

class Converters {
    @TypeConverter
    fun fromServiceInfo(serviceInfo: NsdServiceInfo): String {
        return serviceInfo.toString()
    }

    @TypeConverter
    fun toServiceInfo(serviceInfoString: String): NsdServiceInfo {
        // Implement the conversion logic from String to NsdServiceInfo
        return NsdServiceInfo()
    }

    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    fun toList(listString: String): List<String> {
        return listString.split(",")
    }
}