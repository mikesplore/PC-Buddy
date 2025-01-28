package com.mike.vendor.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mike.vendor.model.dataClasses.MountPointDetails

class Converters {
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

}
