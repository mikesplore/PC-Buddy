package com.mike.pcbuddy.model.repositories

import com.mike.pcbuddy.model.dao.MemoryAndStorageDao
import com.mike.pcbuddy.model.dataClasses.MemoryDetails
import com.mike.pcbuddy.model.dataClasses.StorageInfo
import javax.inject.Inject

class MemoryAndStorageRepo @Inject constructor(
    private val memoryAndStorageDao: MemoryAndStorageDao
) {
    suspend fun insertMemoryDetails(memoryDetails: MemoryDetails) {
        memoryAndStorageDao.insertMemoryDetails(memoryDetails)
    }

    suspend fun insertStorageInfo(storageInfo: StorageInfo) {
        memoryAndStorageDao.insertStorageInfo(storageInfo)
    }

    suspend fun getMemoryDetails(macAddress: String): MemoryDetails {
        return memoryAndStorageDao.getMemoryDetails(macAddress)
    }

    suspend fun getStorageInfo(macAddress: String): StorageInfo {
        return memoryAndStorageDao.getStorageInfo(macAddress)
    }

    suspend fun deleteMemoryDetails(macAddress: String) {
        memoryAndStorageDao.deleteMemoryDetails(macAddress)
    }
}