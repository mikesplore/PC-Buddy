package com.mike.vendor.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.vendor.model.dao.BatteryDao
import com.mike.vendor.model.dao.DisplayDao
import com.mike.vendor.model.dao.MemoryAndStorageDao
import com.mike.vendor.model.dao.ServerDao
import com.mike.vendor.model.dao.SystemInfoDao
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.dataClasses.ComputerSystemDetails
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.OperatingSystemInfo
import com.mike.vendor.model.dataClasses.ProcessorDetails
import com.mike.vendor.model.dataClasses.SoftwareInfo
import com.mike.vendor.model.dataClasses.StorageInfo
import com.mike.vendor.model.dataClasses.UserEnvironmentInfo


@Database(
    entities = [
        Server::class,
        BatteryDetails::class,
        MemoryDetails::class,
        StorageInfo::class,
        DisplayInfo::class,
        UserEnvironmentInfo::class,
        SoftwareInfo::class,
        OperatingSystemInfo::class,
        ComputerSystemDetails::class,
        ProcessorDetails::class

    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serverDao(): ServerDao
    abstract fun batteryDao(): BatteryDao
    abstract fun memoryAndStorageDao(): MemoryAndStorageDao
    abstract fun displayDao(): DisplayDao
    abstract fun systemInfoDao(): SystemInfoDao
}