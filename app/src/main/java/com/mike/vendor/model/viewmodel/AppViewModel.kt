package com.mike.vendor.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.vendor.model.dataClasses.*
import com.mike.vendor.model.DatabaseOperations
import kotlinx.coroutines.launch

class AppViewModel(private val databaseOperations: DatabaseOperations) : ViewModel() {

    fun performDatabaseOperations(
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
        viewModelScope.launch {
            databaseOperations.saveDatabaseData(
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

    fun retrieveDatabaseData() {
        viewModelScope.launch {
            databaseOperations.retrieveDatabaseData()
        }
    }
}