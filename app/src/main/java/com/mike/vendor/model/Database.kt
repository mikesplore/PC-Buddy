package com.mike.vendor.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.vendor.model.dao.AppDao
import com.mike.vendor.model.dao.DeviceDao

@Database(entities = [NetworkDevice::class, AppEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun appDao(): AppDao

}