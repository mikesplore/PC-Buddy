//package com.mike.vendor.model.dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Delete
//import com.mike.vendor.model.dataClasses.*
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface AppDao {
//
//    // Insert operations
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertOperatingSystemInfo(operatingSystemInfo: OperatingSystemInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertHardwareInfo(hardwareInfo: HardwareInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertBatteryDetails(batteryDetails: BatteryDetails)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertProcessorDetails(processorDetails: ProcessorDetails)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMemoryDetails(memoryDetails: MemoryDetails)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDisplayInfo(displayInfo: DisplayInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSoftwareInfo(softwareInfo: SoftwareInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertJvmDetails(jvmDetails: JvmDetails)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNetworkingInfo(networkingInfo: NetworkingInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNetworkInterfaceDetails(networkInterfaceDetails: NetworkInterfaceDetails)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertProcessInfo(processInfo: ProcessInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDetailedProcessInfo(detailedProcessInfo: DetailedProcessInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertStorageInfo(storageInfo: StorageInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMountPointDetails(mountPointDetails: MountPointDetails)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUserEnvironmentInfo(userEnvironmentInfo: UserEnvironmentInfo)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertInstalledApplication(installedApplication: InstalledApplication)
//
//    // Retrieve operations
//    @Query("SELECT * FROM OperatingSystemInfo")
//    fun getOperatingSystemInfo(): Flow<List<OperatingSystemInfo>>
//
//    @Query("SELECT * FROM HardwareInfo")
//    fun getHardwareInfo(): Flow<List<HardwareInfo>>
//
//    @Query("SELECT * FROM BatteryDetails")
//    fun getBatteryDetails(): Flow<List<BatteryDetails>>
//
//    @Query("SELECT * FROM ProcessorInfo")
//    fun getProcessorDetails(): Flow<List<ProcessorDetails>>
//
//    @Query("SELECT * FROM MemoryDetails")
//    fun getMemoryDetails(): Flow<List<MemoryDetails>>
//
//    @Query("SELECT * FROM DisplayInfo")
//    fun getDisplayInfo(): Flow<List<DisplayInfo>>
//
//    @Query("SELECT * FROM SoftwareInfo")
//    fun getSoftwareInfo(): Flow<List<SoftwareInfo>>
//
//    @Query("SELECT * FROM JvmDetails")
//    fun getJvmDetails(): Flow<List<JvmDetails>>
//
//    @Query("SELECT * FROM NetworkingInfo")
//    fun getNetworkingInfo(): Flow<List<NetworkingInfo>>
//
//    @Query("SELECT * FROM NetworkInterfaceDetails")
//    fun getNetworkInterfaceDetails(): Flow<List<NetworkInterfaceDetails>>
//
//    @Query("SELECT * FROM ProcessInfo")
//    fun getProcessInfo(): Flow<List<ProcessInfo>>
//
//    @Query("SELECT * FROM DetailedProcessInfo")
//    fun getDetailedProcessInfo(): Flow<List<DetailedProcessInfo>>
//
//    @Query("SELECT * FROM StorageInfo")
//    fun getStorageInfo(): Flow<List<StorageInfo>>
//
//    @Query("SELECT * FROM MountPointDetails")
//    fun getMountPointDetails(): Flow<List<MountPointDetails>>
//
//    @Query("SELECT * FROM UserEnvironmentInfo")
//    fun getUserEnvironmentInfo(): Flow<List<UserEnvironmentInfo>>
//
//    @Query("SELECT * FROM InstalledApplication")
//    fun getInstalledApplications(): Flow<List<InstalledApplication>>
//
//    // Delete operations
//    @Delete
//    suspend fun deleteOperatingSystemInfo(operatingSystemInfo: OperatingSystemInfo)
//
//    @Delete
//    suspend fun deleteHardwareInfo(hardwareInfo: HardwareInfo)
//
//    @Delete
//    suspend fun deleteBatteryDetails(batteryDetails: BatteryDetails)
//
//    @Delete
//    suspend fun deleteProcessorDetails(processorDetails: ProcessorDetails)
//
//    @Delete
//    suspend fun deleteMemoryDetails(memoryDetails: MemoryDetails)
//
//    @Delete
//    suspend fun deleteDisplayInfo(displayInfo: DisplayInfo)
//
//    @Delete
//    suspend fun deleteSoftwareInfo(softwareInfo: SoftwareInfo)
//
//    @Delete
//    suspend fun deleteJvmDetails(jvmDetails: JvmDetails)
//
//    @Delete
//    suspend fun deleteNetworkingInfo(networkingInfo: NetworkingInfo)
//
//    @Delete
//    suspend fun deleteNetworkInterfaceDetails(networkInterfaceDetails: NetworkInterfaceDetails)
//
//    @Delete
//    suspend fun deleteProcessInfo(processInfo: ProcessInfo)
//
//    @Delete
//    suspend fun deleteDetailedProcessInfo(detailedProcessInfo: DetailedProcessInfo)
//
//    @Delete
//    suspend fun deleteStorageInfo(storageInfo: StorageInfo)
//
//    @Delete
//    suspend fun deleteMountPointDetails(mountPointDetails: MountPointDetails)
//
//    @Delete
//    suspend fun deleteUserEnvironmentInfo(userEnvironmentInfo: UserEnvironmentInfo)
//
//    @Delete
//    suspend fun deleteInstalledApplication(installedApplication: InstalledApplication)
//}