package com.mike.vendor.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.vendor.model.dao.AppDao
import com.mike.vendor.model.dao.DeviceDao
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.dataClasses.DetailedProcessInfo
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.dataClasses.HardwareInfo
import com.mike.vendor.model.dataClasses.InstalledApplication
import com.mike.vendor.model.dataClasses.JvmDetails
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.MountPointDetails
import com.mike.vendor.model.dataClasses.NetworkInterfaceDetails
import com.mike.vendor.model.dataClasses.NetworkingInfo
import com.mike.vendor.model.dataClasses.OperatingSystemInfo
import com.mike.vendor.model.dataClasses.ProcessInfo
import com.mike.vendor.model.dataClasses.ProcessorDetails
import com.mike.vendor.model.dataClasses.SoftwareInfo
import com.mike.vendor.model.dataClasses.StorageInfo
import com.mike.vendor.model.dataClasses.UserEnvironmentInfo

@Database(
    entities = [
        NetworkDevice::class,
        OperatingSystemInfo::class,
        HardwareInfo::class,
        BatteryDetails::class,
        ProcessorDetails::class,
        MemoryDetails::class,
        DisplayInfo::class,
        SoftwareInfo::class,
        JvmDetails::class,
        NetworkingInfo::class,
        NetworkInterfaceDetails::class,
        ProcessInfo::class,
        DetailedProcessInfo::class,
        StorageInfo::class,
        MountPointDetails::class,
        UserEnvironmentInfo::class,
        InstalledApplication::class

    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun appDao(): AppDao

}