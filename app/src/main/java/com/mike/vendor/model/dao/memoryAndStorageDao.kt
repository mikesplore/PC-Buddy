package com.mike.vendor.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.StorageInfo

@Dao
interface MemoryAndStorageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemoryDetails(memoryDetails: MemoryDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorageInfo(storageInfo: StorageInfo)

    @Query("SELECT * FROM memory_details WHERE macAddress = :macAddress")
    suspend fun getMemoryDetails(macAddress: String): MemoryDetails

    @Query("SELECT * FROM storage_info WHERE macAddress = :macAddress")
    suspend fun getStorageInfo(macAddress: String): StorageInfo


}

