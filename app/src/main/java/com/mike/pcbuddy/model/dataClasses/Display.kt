package com.mike.pcbuddy.model.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "display_info")
data class DisplayInfo(
    @PrimaryKey val macAddress: String,
    val deviceName: String,
    val width: Int,
    val height: Int,
    val refreshRate: Int
){
    constructor(macAddress: String, deviceName: String) : this(macAddress, deviceName, 0, 0, 0)
}