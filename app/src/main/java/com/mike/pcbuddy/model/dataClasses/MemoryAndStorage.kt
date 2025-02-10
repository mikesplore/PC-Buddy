package com.mike.pcbuddy.model.dataClasses

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
){
    constructor() : this("", 0, 0, 0, 0, 0, 0)
}

@Entity(tableName = "storage_info")
data class StorageInfo(
    @PrimaryKey val macAddress: String,
    val mountPoints: List<MountPointDetails>,
    val totalSpace: Long,
    val usedSpace: Long,
    val freeSpace: Long
){
    constructor() : this("", emptyList(), 0, 0, 0)
}

data class MountPointDetails(
    val mountPoint: String,
    val type: String,
    val description: String,
    val total: Long,
    val used: Long,
    val available: Long,
    val usagePercentage: Double
)
