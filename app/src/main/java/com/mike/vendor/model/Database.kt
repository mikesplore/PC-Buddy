package com.mike.vendor.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NetworkDevice::class], version = 1)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}