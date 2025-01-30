package com.mike.vendor.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mike.vendor.model.dataClasses.MountPointDetails
import java.util.Locale

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

    @TypeConverter
    fun fromListString(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toListString(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLongArray(value: LongArray): String {
        val gson = Gson()
        val type = object : TypeToken<LongArray>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLongArray(value: String): LongArray {
        val gson = Gson()
        val type = object : TypeToken<LongArray>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLocale(value: Locale): String {
        return value.toString()
    }

    @TypeConverter
    fun toLocale(value: String): Locale {
        return Locale(value)
    }

}
