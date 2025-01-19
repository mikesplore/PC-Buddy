package com.mike.vendor.model.database



import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mike.vendor.model.users.UserType

class Converters {
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromEnumList(houseAmenities: List<UserType>): String {
        return Gson().toJson(houseAmenities)
    }

    @TypeConverter
    fun toEnumList(houseAmenitiesString: String): List<UserType> {
        val type = object : TypeToken<List<UserType>>() {}.type
        return Gson().fromJson(houseAmenitiesString, type)
    }
}
