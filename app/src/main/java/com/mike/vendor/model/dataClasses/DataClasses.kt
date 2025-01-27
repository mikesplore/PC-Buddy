//package com.mike.vendor.model.dataClasses
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import java.util.Locale
//
//@Entity(tableName = "OperatingSystemInfo")
//data class OperatingSystemInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val version: String,
//    val architecture: String,
//    val manufacturer: String,
//    val buildNumber: String,
//    val installDate: Long?
//){
//    constructor(name: String, version: String, architecture: String, manufacturer: String, buildNumber: String, installDate: Long?) : this(0, name, version, architecture, manufacturer, buildNumber, installDate)
//}
//
//
//@Entity(tableName = "HardwareInfo")
//data class HardwareInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val computerSystem: String,
//    val processor: String,
//    val memory: String,
//    val battery: String?,
//    val displays: String
//){
//    constructor(computerSystem: String, processor: String, memory: String, battery: String?, displays: String) : this(0, computerSystem, processor, memory, battery, displays)
//}
//
//@Entity(tableName = "BatteryInfo")
//data class ComputerSystemDetails(
//    val manufacturer: String,
//    val model: String,
//    val serialNumber: String
//){
//    @PrimaryKey(autoGenerate = true) val id: Int = 0
//}
//
//
//@Entity(tableName = "ProcessorInfo")
//data class ProcessorDetails(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val physicalCoreCount: Int,
//    val logicalCoreCount: Int,
//    val maxFrequency: Long,
//    val currentFrequency: LongArray
//) {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as ProcessorDetails
//
//        if (name != other.name) return false
//        if (physicalCoreCount != other.physicalCoreCount) return false
//        if (logicalCoreCount != other.logicalCoreCount) return false
//        if (maxFrequency != other.maxFrequency) return false
//        if (!currentFrequency.contentEquals(other.currentFrequency)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = name.hashCode()
//        result = 31 * result + physicalCoreCount
//        result = 31 * result + logicalCoreCount
//        result = 31 * result + maxFrequency.hashCode()
//        result = 31 * result + currentFrequency.contentHashCode()
//        return result
//    }
//}
//
//@Entity(tableName = "MemoryDetails")
//data class MemoryDetails(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val total: Long,
//    val available: Long,
//    val used: Long,
//    val cacheSize: Long,
//    val swapTotal: Long,
//    val swapUsed: Long
//){
//    constructor(total: Long, available: Long, used: Long, cacheSize: Long, swapTotal: Long, swapUsed: Long) : this(0, total, available, used, cacheSize, swapTotal, swapUsed)
//}
//
//@Entity(tableName = "BatteryDetails")
//data class BatteryDetails(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val remainingCapacity: Double,
//    val timeRemaining: Long,
//    val voltage: Double,
//    val temperature: Double,
//    val isCharging: Boolean,
//    val cycleCount: Int,
//    val designCapacity: Int,
//    val fullChargeCapacity: Int,
//    val batteryHealth: Double,
//    val batteryChemistry: String,
//    val batteryStatus: String,
//    val manufactureDate: Any,
//    val serialNumber: String
//){
//    constructor(name: String, remainingCapacity: Double, timeRemaining: Long, voltage: Double, temperature: Double, isCharging: Boolean, cycleCount: Int, designCapacity: Int, fullChargeCapacity: Int, batteryHealth: Double,
//                batteryChemistry: String, batteryStatus: String, manufactureDate: Any, serialNumber: String) : this(0, name, remainingCapacity, timeRemaining, voltage, temperature, isCharging, cycleCount, designCapacity, fullChargeCapacity, batteryHealth,
//        batteryChemistry, batteryStatus, manufactureDate, serialNumber)
//}
//
//@Entity(tableName = "DisplayInfo")
//data class DisplayInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val deviceName: String,
//    val width: Int,
//    val height: Int,
//    val refreshRate: Int
//){
//    constructor(deviceName: String, width: Int, height: Int, refreshRate: Int) : this(0, deviceName, width, height, refreshRate)
//}
//
//@Entity(tableName = "SoftwareInfo")
//data class SoftwareInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val runningProcessCount: Int,
//    val systemUptime: Long,
//    val jvmDetails: JvmDetails,
//    val environmentVariables: Map<String, String>
//){
//    constructor(runningProcessCount: Int, systemUptime: Long, jvmDetails: JvmDetails, environmentVariables: Map<String, String>) : this(0, runningProcessCount, systemUptime, jvmDetails, environmentVariables)
//
//}
//
//@Entity(tableName = "JvmDetails")
//data class JvmDetails(
//    val version: String,
//    val vendor: String,
//    val home: String,
//    val availableProcessors: Int,
//    val maxMemory: Long
//){
//    @PrimaryKey(autoGenerate = true) val id: Int = 0
//}
//
//
//@Entity(tableName = "NetworkingInfo")
//data class NetworkingInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val hostname: String,
//    val domainName: String,
//    val networkInterfaces: List<NetworkInterfaceDetails>,
//    val dnsDomainName: String,
//    val dnsHostName: String
//){
//    constructor(hostname: String, domainName: String, networkInterfaces: List<NetworkInterfaceDetails>, dnsDomainName: String, dnsHostName: String) : this(0, hostname, domainName, networkInterfaces, dnsDomainName, dnsHostName)
//}
//
//@Entity(tableName = "NetworkInterfaceDetails")
//data class NetworkInterfaceDetails(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val displayName: String,
//    val ipv4Address: List<String>,
//    val ipv6Address: List<String>,
//    val macAddress: String,
//    val mtu: Int,
//    val isUp: Boolean,
//    val isVirtual: Boolean
//){
//    constructor(name: String, displayName: String, ipv4Address: List<String>, ipv6Address: List<String>, macAddress: String, mtu: Int, isUp: Boolean, isVirtual: Boolean) : this(0, name, displayName, ipv4Address, ipv6Address, macAddress, mtu, isUp, isVirtual)
//}
//
//
//@Entity(tableName = "ProcessInfo")
//data class ProcessInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val total: Int,
//    val running: Int,
//    val topCpuIntensiveProcesses: List<DetailedProcessInfo>
//){
//    constructor(total: Int, running: Int, topCpuIntensiveProcesses: List<DetailedProcessInfo>) : this(0, total, running, topCpuIntensiveProcesses)
//}
//
//@Entity(tableName = "DetailedProcessInfo")
//data class DetailedProcessInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val pid: Int,
//    val name: String,
//    val cpuUsage: Double,
//    val memoryUsage: Long,
//    val startTime: Long,
//    val path: String
//){
//    constructor(pid: Int, name: String, cpuUsage: Double, memoryUsage: Long, startTime: Long, path: String) : this(0, pid, name, cpuUsage, memoryUsage, startTime, path)
//}
//
//@Entity(tableName = "StorageInfo")
//data class StorageInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val mountPoints: List<MountPointDetails>,
//    val totalSpace: Long,
//    val usedSpace: Long,
//    val freeSpace: Long
//){
//    constructor(mountPoints: List<MountPointDetails>, totalSpace: Long, usedSpace: Long, freeSpace: Long) : this(0, mountPoints, totalSpace, usedSpace, freeSpace)
//
//}
//
//@Entity(tableName = "MountPointDetails")
//data class MountPointDetails(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val mountPoint: String,
//    val type: String,
//    val description: String,
//    val total: Long,
//    val used: Long,
//    val available: Long,
//    val usagePercentage: Double
//){
//    constructor(mountPoint: String, type: String, description: String, total: Long, used: Long, available: Long, usagePercentage: Double) : this(0, mountPoint, type, description, total, used, available, usagePercentage)
//
//}
//
//@Entity(tableName = "UserEnvironmentInfo")
//data class UserEnvironmentInfo(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val username: String,
//    val homeDirectory: String,
//    val language: String,
//    val country: String,
//    val timezone: String,
//    val locale: Locale
//){
//    constructor(username: String, homeDirectory: String, language: String, country: String, timezone: String, locale: Locale) : this(0, username, homeDirectory, language, country, timezone, locale)
//}
//
//
//@Entity(tableName = "InstalledApplication")
//data class InstalledApplication(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val version: String,
//    val isRunning: Boolean,
//    val installDate: Long,
//    val publisher: String,
//    val path: String
//){
//    constructor(name: String, version: String, isRunning: Boolean, installDate: Long, publisher: String, path: String) : this(0, name, version, isRunning, installDate, publisher, path)
//}
//
