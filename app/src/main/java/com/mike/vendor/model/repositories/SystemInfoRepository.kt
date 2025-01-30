package com.mike.vendor.model.repositories

import com.mike.vendor.model.dao.SystemInfoDao
import com.mike.vendor.model.dataClasses.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SystemInfoRepository @Inject constructor(private val systemInfoDao: SystemInfoDao) {

    suspend fun insertProcessorDetails(processorDetails: ProcessorDetails) {
        systemInfoDao.insertProcessorDetails(processorDetails)
    }

    suspend fun insertComputerSystemDetails(computerSystemDetails: ComputerSystemDetails) {
        systemInfoDao.insertComputerSystemDetails(computerSystemDetails)
    }

    suspend fun insertOperatingSystemInfo(operatingSystemInfo: OperatingSystemInfo) {
        systemInfoDao.insertOperatingSystemInfo(operatingSystemInfo)
    }

    suspend fun insertSoftwareInfo(softwareInfo: SoftwareInfo) {
        systemInfoDao.insertSoftwareInfo(softwareInfo)
    }

    suspend fun insertUserEnvironmentInfo(userEnvironmentInfo: UserEnvironmentInfo) {
        systemInfoDao.insertUserEnvironmentInfo(userEnvironmentInfo)
    }

    // Updated to return actual data (not Flow)
    fun getProcessorDetails(macAddress: String): Flow<ProcessorDetails> {
        return systemInfoDao.getProcessorDetails(macAddress)
    }

    fun getComputerSystemDetails(macAddress: String): Flow<ComputerSystemDetails> {
        return systemInfoDao.getComputerSystemDetails(macAddress)
    }

    fun getOperatingSystemInfo(macAddress: String): Flow<OperatingSystemInfo> {
        return systemInfoDao.getOperatingSystemInfo(macAddress)
    }

    fun getSoftwareInfo(macAddress: String): Flow<SoftwareInfo> {
        return systemInfoDao.getSoftwareInfo(macAddress)
    }

    fun getUserEnvironmentInfo(macAddress: String): Flow<UserEnvironmentInfo> {
        return systemInfoDao.getUserEnvironmentInfo(macAddress)
    }
}

