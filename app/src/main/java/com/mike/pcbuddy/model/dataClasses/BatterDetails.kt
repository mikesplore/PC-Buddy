package com.mike.pcbuddy.model.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery")
data class BatteryDetails(
    @PrimaryKey val macAddress: String,
    val name: String,
    val remainingCapacity: Double,
    val timeRemaining: Long,
    val voltage: Double,
    val temperature: Double,
    val isCharging: Boolean,
    val cycleCount: Int,
    val designCapacity: Int,
    val fullChargeCapacity: Int,
    val batteryHealth: Double,
    val batteryChemistry: String,
    val batteryStatus: String,
    val manufactureDate: String,
    val serialNumber: String
){
    constructor(): this("", "", 0.0, 0, 0.0, 0.0, false, 0, 0, 0, 0.0, "", "", "", "")
}