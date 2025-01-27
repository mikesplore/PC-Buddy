//package com.mike.vendor.model.repositories
//
//import com.mike.vendor.model.dao.AppDao
//import com.mike.vendor.model.dataClasses.*
//import kotlinx.coroutines.flow.Flow
//
//class AppRepository(private val appDao: AppDao) {
//
//    // Insert operations
//    suspend fun insertOperatingSystemInfo(operatingSystemInfo: OperatingSystemInfo) {
//        appDao.insertOperatingSystemInfo(operatingSystemInfo)
//    }
//
//    suspend fun insertHardwareInfo(hardwareInfo: HardwareInfo) {
//        appDao.insertHardwareInfo(hardwareInfo)
//    }
//
//    suspend fun insertBatteryDetails(batteryDetails: BatteryDetails) {
//        appDao.insertBatteryDetails(batteryDetails)
//    }
//
//    suspend fun insertProcessorDetails(processorDetails: ProcessorDetails) {
//        appDao.insertProcessorDetails(processorDetails)
//    }
//
//    suspend fun insertMemoryDetails(memoryDetails: MemoryDetails) {
//        appDao.insertMemoryDetails(memoryDetails)
//    }
//
//    suspend fun insertDisplayInfo(displayInfo: DisplayInfo) {
//        appDao.insertDisplayInfo(displayInfo)
//    }
//
//    suspend fun insertSoftwareInfo(softwareInfo: SoftwareInfo) {
//        appDao.insertSoftwareInfo(softwareInfo)
//    }
//
//    suspend fun insertJvmDetails(jvmDetails: JvmDetails) {
//        appDao.insertJvmDetails(jvmDetails)
//    }
//
//    suspend fun insertNetworkingInfo(networkingInfo: NetworkingInfo) {
//        appDao.insertNetworkingInfo(networkingInfo)
//    }
//
//    suspend fun insertNetworkInterfaceDetails(networkInterfaceDetails: NetworkInterfaceDetails) {
//        appDao.insertNetworkInterfaceDetails(networkInterfaceDetails)
//    }
//
//    suspend fun insertProcessInfo(processInfo: ProcessInfo) {
//        appDao.insertProcessInfo(processInfo)
//    }
//
//    suspend fun insertDetailedProcessInfo(detailedProcessInfo: DetailedProcessInfo) {
//        appDao.insertDetailedProcessInfo(detailedProcessInfo)
//    }
//
//    suspend fun insertStorageInfo(storageInfo: StorageInfo) {
//        appDao.insertStorageInfo(storageInfo)
//    }
//
//    suspend fun insertMountPointDetails(mountPointDetails: MountPointDetails) {
//        appDao.insertMountPointDetails(mountPointDetails)
//    }
//
//    suspend fun insertUserEnvironmentInfo(userEnvironmentInfo: UserEnvironmentInfo) {
//        appDao.insertUserEnvironmentInfo(userEnvironmentInfo)
//    }
//
//    suspend fun insertInstalledApplication(installedApplication: InstalledApplication) {
//        appDao.insertInstalledApplication(installedApplication)
//    }
//
//    // Retrieve operations
//    fun getOperatingSystemInfo(): Flow<List<OperatingSystemInfo>> = appDao.getOperatingSystemInfo()
//
//    fun getHardwareInfo(): Flow<List<HardwareInfo>> = appDao.getHardwareInfo()
//
//    fun getBatteryDetails(): Flow<List<BatteryDetails>> = appDao.getBatteryDetails()
//
//    fun getProcessorDetails(): Flow<List<ProcessorDetails>> = appDao.getProcessorDetails()
//
//    fun getMemoryDetails(): Flow<List<MemoryDetails>> = appDao.getMemoryDetails()
//
//    fun getDisplayInfo(): Flow<List<DisplayInfo>> = appDao.getDisplayInfo()
//
//    fun getSoftwareInfo(): Flow<List<SoftwareInfo>> = appDao.getSoftwareInfo()
//
//    fun getJvmDetails(): Flow<List<JvmDetails>> = appDao.getJvmDetails()
//
//    fun getNetworkingInfo(): Flow<List<NetworkingInfo>> = appDao.getNetworkingInfo()
//
//    fun getNetworkInterfaceDetails(): Flow<List<NetworkInterfaceDetails>> = appDao.getNetworkInterfaceDetails()
//
//    fun getProcessInfo(): Flow<List<ProcessInfo>> = appDao.getProcessInfo()
//
//    fun getDetailedProcessInfo(): Flow<List<DetailedProcessInfo>> = appDao.getDetailedProcessInfo()
//
//    fun getStorageInfo(): Flow<List<StorageInfo>> = appDao.getStorageInfo()
//
//    fun getMountPointDetails(): Flow<List<MountPointDetails>> = appDao.getMountPointDetails()
//
//    fun getUserEnvironmentInfo(): Flow<List<UserEnvironmentInfo>> = appDao.getUserEnvironmentInfo()
//
//    fun getInstalledApplications(): Flow<List<InstalledApplication>> = appDao.getInstalledApplications()
//
//    // Delete operations
//    suspend fun deleteOperatingSystemInfo(operatingSystemInfo: OperatingSystemInfo) {
//        appDao.deleteOperatingSystemInfo(operatingSystemInfo)
//    }
//
//    suspend fun deleteHardwareInfo(hardwareInfo: HardwareInfo) {
//        appDao.deleteHardwareInfo(hardwareInfo)
//    }
//
//    suspend fun deleteBatteryDetails(batteryDetails: BatteryDetails) {
//        appDao.deleteBatteryDetails(batteryDetails)
//    }
//
//    suspend fun deleteProcessorDetails(processorDetails: ProcessorDetails) {
//        appDao.deleteProcessorDetails(processorDetails)
//    }
//
//    suspend fun deleteMemoryDetails(memoryDetails: MemoryDetails) {
//        appDao.deleteMemoryDetails(memoryDetails)
//    }
//
//    suspend fun deleteDisplayInfo(displayInfo: DisplayInfo) {
//        appDao.deleteDisplayInfo(displayInfo)
//    }
//
//    suspend fun deleteSoftwareInfo(softwareInfo: SoftwareInfo) {
//        appDao.deleteSoftwareInfo(softwareInfo)
//    }
//
//    suspend fun deleteJvmDetails(jvmDetails: JvmDetails) {
//        appDao.deleteJvmDetails(jvmDetails)
//    }
//
//    suspend fun deleteNetworkingInfo(networkingInfo: NetworkingInfo) {
//        appDao.deleteNetworkingInfo(networkingInfo)
//    }
//
//    suspend fun deleteNetworkInterfaceDetails(networkInterfaceDetails: NetworkInterfaceDetails) {
//        appDao.deleteNetworkInterfaceDetails(networkInterfaceDetails)
//    }
//
//    suspend fun deleteProcessInfo(processInfo: ProcessInfo) {
//        appDao.deleteProcessInfo(processInfo)
//    }
//
//    suspend fun deleteDetailedProcessInfo(detailedProcessInfo: DetailedProcessInfo) {
//        appDao.deleteDetailedProcessInfo(detailedProcessInfo)
//    }
//
//    suspend fun deleteStorageInfo(storageInfo: StorageInfo) {
//        appDao.deleteStorageInfo(storageInfo)
//    }
//
//    suspend fun deleteMountPointDetails(mountPointDetails: MountPointDetails) {
//        appDao.deleteMountPointDetails(mountPointDetails)
//    }
//
//    suspend fun deleteUserEnvironmentInfo(userEnvironmentInfo: UserEnvironmentInfo) {
//        appDao.deleteUserEnvironmentInfo(userEnvironmentInfo)
//    }
//
//    suspend fun deleteInstalledApplication(installedApplication: InstalledApplication) {
//        appDao.deleteInstalledApplication(installedApplication)
//    }
//}