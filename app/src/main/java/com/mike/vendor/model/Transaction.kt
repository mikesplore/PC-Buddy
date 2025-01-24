package com.mike.vendor.model

import androidx.room.Transaction
import com.mike.vendor.model.dataClasses.*
import com.mike.vendor.model.repositories.AppRepository


class DatabaseOperations(private val appRepository: AppRepository) {

    @Transaction
    suspend fun saveDatabaseData(
        operatingSystemInfo: OperatingSystemInfo,
        hardwareInfo: HardwareInfo,
        batteryDetails: BatteryDetails,
        processorDetails: ProcessorDetails,
        memoryDetails: MemoryDetails,
        displayInfo: DisplayInfo,
        softwareInfo: SoftwareInfo,
        jvmDetails: JvmDetails,
        networkingInfo: NetworkingInfo,
        networkInterfaceDetails: NetworkInterfaceDetails,
        processInfo: ProcessInfo,
        detailedProcessInfo: DetailedProcessInfo,
        storageInfo: StorageInfo,
        mountPointDetails: MountPointDetails,
        userEnvironmentInfo: UserEnvironmentInfo,
        installedApplication: InstalledApplication
    ) {
        appRepository.insertOperatingSystemInfo(operatingSystemInfo)
        appRepository.insertHardwareInfo(hardwareInfo)
        appRepository.insertBatteryDetails(batteryDetails)
        appRepository.insertProcessorDetails(processorDetails)
        appRepository.insertMemoryDetails(memoryDetails)
        appRepository.insertDisplayInfo(displayInfo)
        appRepository.insertSoftwareInfo(softwareInfo)
        appRepository.insertJvmDetails(jvmDetails)
        appRepository.insertNetworkingInfo(networkingInfo)
        appRepository.insertNetworkInterfaceDetails(networkInterfaceDetails)
        appRepository.insertProcessInfo(processInfo)
        appRepository.insertDetailedProcessInfo(detailedProcessInfo)
        appRepository.insertStorageInfo(storageInfo)
        appRepository.insertMountPointDetails(mountPointDetails)
        appRepository.insertUserEnvironmentInfo(userEnvironmentInfo)
        appRepository.insertInstalledApplication(installedApplication)
    }

    @Transaction
    fun retrieveDatabaseData(): List<Any> {
        val operatingSystemInfo = appRepository.getOperatingSystemInfo()
        val hardwareInfo = appRepository.getHardwareInfo()
        val batteryDetails = appRepository.getBatteryDetails()
        val processorDetails = appRepository.getProcessorDetails()
        val memoryDetails = appRepository.getMemoryDetails()
        val displayInfo = appRepository.getDisplayInfo()
        val softwareInfo = appRepository.getSoftwareInfo()
        val jvmDetails = appRepository.getJvmDetails()
        val networkingInfo = appRepository.getNetworkingInfo()
        val networkInterfaceDetails = appRepository.getNetworkInterfaceDetails()
        val processInfo = appRepository.getProcessInfo()
        val detailedProcessInfo = appRepository.getDetailedProcessInfo()
        val storageInfo = appRepository.getStorageInfo()
        val mountPointDetails = appRepository.getMountPointDetails()
        val userEnvironmentInfo = appRepository.getUserEnvironmentInfo()
        val installedApplication = appRepository.getInstalledApplications()

        return listOf(
            operatingSystemInfo,
            hardwareInfo,
            batteryDetails,
            processorDetails,
            memoryDetails,
            displayInfo,
            softwareInfo,
            jvmDetails,
            networkingInfo,
            networkInterfaceDetails,
            processInfo,
            detailedProcessInfo,
            storageInfo,
            mountPointDetails,
            userEnvironmentInfo,
            installedApplication
        )
    }
}