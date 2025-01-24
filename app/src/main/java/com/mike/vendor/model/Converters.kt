package com.mike.vendor.model

import androidx.room.TypeConverter
import android.net.nsd.NsdServiceInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mike.vendor.model.dataClasses.DetailedProcessInfo
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.dataClasses.MountPointDetails
import com.mike.vendor.model.dataClasses.NetworkInterfaceDetails

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

    @TypeConverter
    fun fromMountPointDetailsList(value: List<MountPointDetails>): String {
        val gson = Gson()
        val type = object : TypeToken<List<MountPointDetails>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMountPointDetailsList(value: String): List<MountPointDetails> {
        val gson = Gson()
        val type = object : TypeToken<List<MountPointDetails>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDisplayInfoList(value: List<DisplayInfo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<DisplayInfo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDisplayInfoList(value: String): List<DisplayInfo> {
        val gson = Gson()
        val type = object : TypeToken<List<DisplayInfo>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromNetworkInterfaceDetailsList(value: List<NetworkInterfaceDetails>): String {
        val gson = Gson()
        val type = object : TypeToken<List<NetworkInterfaceDetails>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toNetworkInterfaceDetailsList(value: String): List<NetworkInterfaceDetails> {
        val gson = Gson()
        val type = object : TypeToken<List<NetworkInterfaceDetails>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDetailedProcessInfoList(value: List<DetailedProcessInfo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<DetailedProcessInfo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDetailedProcessInfoList(value: String): List<DetailedProcessInfo> {
        val gson = Gson()
        val type = object : TypeToken<List<DetailedProcessInfo>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

}