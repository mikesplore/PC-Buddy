package com.mike.vendor.model.repositories

import com.mike.vendor.model.dao.MemoryAndStorageDao
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.StorageInfo
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
}