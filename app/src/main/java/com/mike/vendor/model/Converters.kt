package com.mike.vendor.model

import android.net.nsd.NsdServiceInfo
import androidx.room.TypeConverter

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

    fun fromMap(map: Map<String, String>): String {
        return map.map { "${it.key}:${it.value}" }.joinToString(",")
    }

    fun toMap(mapString: String): Map<String, String> {
        return mapString.split(",").associate {
            val (key, value) = it.split(":")
            key to value
        }
    }


}
