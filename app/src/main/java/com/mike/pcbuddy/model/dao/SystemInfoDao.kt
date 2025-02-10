package com.mike.pcbuddy.model.dao

import androidx.room.*
import com.mike.pcbuddy.model.dataClasses.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SystemInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProcessorDetails(processorDetails: ProcessorDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComputerSystemDetails(computerSystemDetails: ComputerSystemDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperatingSystemInfo(operatingSystemInfo: OperatingSystemInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoftwareInfo(softwareInfo: SoftwareInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEnvironmentInfo(userEnvironmentInfo: UserEnvironmentInfo)

    @Transaction
    suspend fun insertAllSystemInfo(
        processorDetails: ProcessorDetails,
        computerSystemDetails: ComputerSystemDetails,
        operatingSystemInfo: OperatingSystemInfo,
        softwareInfo: SoftwareInfo,
        userEnvironmentInfo: UserEnvironmentInfo
    ) {
        insertProcessorDetails(processorDetails)
        insertComputerSystemDetails(computerSystemDetails)
        insertOperatingSystemInfo(operatingSystemInfo)
        insertSoftwareInfo(softwareInfo)
        insertUserEnvironmentInfo(userEnvironmentInfo)
    }

    @Query("SELECT * FROM processor_details WHERE macAddress = :macAddress")
    fun getProcessorDetails(macAddress: String): Flow<ProcessorDetails>

    @Query("SELECT * FROM computer_system_details WHERE macAddress = :macAddress")
    fun getComputerSystemDetails(macAddress: String): Flow<ComputerSystemDetails>

    @Query("SELECT * FROM operating_system_info WHERE macAddress = :macAddress")
    fun getOperatingSystemInfo(macAddress: String): Flow<OperatingSystemInfo>

    @Query("SELECT * FROM software_info WHERE macAddress = :macAddress")
    fun getSoftwareInfo(macAddress: String): Flow<SoftwareInfo>

    @Query("SELECT * FROM user_environment_info WHERE macAddress = :macAddress")
    fun getUserEnvironmentInfo(macAddress: String): Flow<UserEnvironmentInfo>

    @Query("DELETE FROM processor_details WHERE macAddress = :macAddress")
    suspend fun deleteProcessorDetails(macAddress: String)

    @Query("DELETE FROM computer_system_details WHERE macAddress = :macAddress")
    suspend fun deleteComputerSystemDetails(macAddress: String)

    @Query("DELETE FROM operating_system_info WHERE macAddress = :macAddress")
    suspend fun deleteOperatingSystemInfo(macAddress: String)

    @Query("DELETE FROM software_info WHERE macAddress = :macAddress")
    suspend fun deleteSoftwareInfo(macAddress: String)

    @Query("DELETE FROM user_environment_info WHERE macAddress = :macAddress")
    suspend fun deleteUserEnvironmentInfo(macAddress: String)


    @Transaction
    suspend fun deleteAllSystemInfo(macAddress: String) {
        deleteProcessorDetails(macAddress)
        deleteComputerSystemDetails(macAddress)
        deleteOperatingSystemInfo(macAddress)
        deleteSoftwareInfo(macAddress)
        deleteUserEnvironmentInfo(macAddress)
    }

}
