package com.mike.vendor.model.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow
import java.util.Locale

@Entity(tableName = "processor_details")
data class ProcessorDetails(
    @PrimaryKey val macAddress: String,
    val name: String,
    val physicalCoreCount: Int,
    val logicalCoreCount: Int,
    val maxFrequency: Long,
    val currentFrequency: LongArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProcessorDetails

        if (name != other.name) return false
        if (physicalCoreCount != other.physicalCoreCount) return false
        if (logicalCoreCount != other.logicalCoreCount) return false
        if (maxFrequency != other.maxFrequency) return false
        if (!currentFrequency.contentEquals(other.currentFrequency)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + physicalCoreCount
        result = 31 * result + logicalCoreCount
        result = 31 * result + maxFrequency.hashCode()
        result = 31 * result + currentFrequency.contentHashCode()
        return result
    }

    constructor() : this("","", 0, 0, 0, longArrayOf())
}


@Entity(tableName = "computer_system_details")
data class ComputerSystemDetails(
    @PrimaryKey val macAddress: String,
    val manufacturer: String,
    val model: String,
    val serialNumber: String
) {
    constructor() : this("","", "", "")
}


@Entity(tableName = "operating_system_info")
data class OperatingSystemInfo(
    @PrimaryKey val macAddress: String,
    val name: String,
    val version: String,
    val architecture: String,
    val manufacturer: String,
    val buildNumber: String,
    val installDate: Long?
) {
    constructor() : this("","", "", "", "", "", null)
}


@Entity(tableName = "software_info")
data class SoftwareInfo(
    @PrimaryKey val macAddress: String,
    val runningProcessCount: Int,
    val systemUptime: Long,

    ) {
    constructor() : this("",0, 0)
}


@Entity(tableName = "user_environment_info")
data class UserEnvironmentInfo(
    @PrimaryKey val macAddress: String,
    val username: String,
    val homeDirectory: String,
    val language: String,
    val country: String,
    val timezone: String,
    val locale: Locale
) {
    constructor() : this("","", "", "", "", "", Locale.getDefault())
}


data class SystemInfo(
    val processorDetails: ProcessorDetails?,
    val computerSystemDetails: ComputerSystemDetails?,
    val operatingSystemInfo: OperatingSystemInfo?,
    val softwareInfo: SoftwareInfo?,
    val userEnvironmentInfo: UserEnvironmentInfo?
)
