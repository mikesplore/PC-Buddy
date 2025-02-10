package com.mike.pcbuddy.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.pcbuddy.model.dao.BatteryDao
import com.mike.pcbuddy.model.dao.DisplayDao
import com.mike.pcbuddy.model.dao.MemoryAndStorageDao
import com.mike.pcbuddy.model.dao.ServerDao
import com.mike.pcbuddy.model.dao.SystemInfoDao
import com.mike.pcbuddy.model.dataClasses.BatteryDetails
import com.mike.pcbuddy.model.dataClasses.ComputerSystemDetails
import com.mike.pcbuddy.model.dataClasses.DisplayInfo
import com.mike.pcbuddy.model.dataClasses.MemoryDetails
import com.mike.pcbuddy.model.dataClasses.OperatingSystemInfo
import com.mike.pcbuddy.model.dataClasses.ProcessorDetails
import com.mike.pcbuddy.model.dataClasses.SoftwareInfo
import com.mike.pcbuddy.model.dataClasses.StorageInfo
import com.mike.pcbuddy.model.dataClasses.UserEnvironmentInfo


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