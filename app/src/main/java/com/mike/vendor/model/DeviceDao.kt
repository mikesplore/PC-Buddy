package com.mike.vendor.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: NetworkDevice)

    @Query("SELECT * FROM network_devices")
    suspend fun getAllDevices(): Flow<List<NetworkDevice>>

    @Query("SELECT * FROM network_devices WHERE id = :id")
    suspend fun getDeviceById(id: Long): NetworkDevice?

}