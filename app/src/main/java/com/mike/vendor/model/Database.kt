package com.mike.vendor.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.vendor.model.dao.BatteryDao
import com.mike.vendor.model.dao.MemoryAndStorageDao
import com.mike.vendor.model.dao.ServerDao
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.StorageInfo


@Database(
    entities = [
       Server::class,
       BatteryDetails::class,
         MemoryDetails::class,
            StorageInfo::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serverDao(): ServerDao
    abstract fun batteryDao(): BatteryDao
    abstract fun memoryAndStorageDao(): MemoryAndStorageDao
}