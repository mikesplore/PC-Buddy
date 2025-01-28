package com.mike.vendor.model.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_details")
data class MemoryDetails(
   @PrimaryKey val macAddress: String,
    val total: Long,
    val available: Long,
    val used: Long,
    val cacheSize: Long,
    val swapTotal: Long,
    val swapUsed: Long
)

@Entity(tableName = "storage_info")
data class StorageInfo(
    @PrimaryKey val macAddress: String,
    val mountPoints: List<MountPointDetails>,
    val totalSpace: Long,
    val usedSpace: Long,
    val freeSpace: Long
)

data class MountPointDetails(
    @PrimaryKey val macAddress: String,
    val mountPoint: String,
    val type: String,
    val description: String,
    val total: Long,
    val used: Long,
    val available: Long,
    val usagePercentage: Double
)
