package com.mike.vendor.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.vendor.model.NetworkDevice
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing and managing `NetworkDevice` entities in the database.
 */
@Dao
interface DeviceDao {

    /**
     * Inserts a `NetworkDevice` into the database.
     * If the device already exists, it will be replaced.
     *
     * @param device The `NetworkDevice` to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(device: NetworkDevice)

    /**
     * Retrieves all `NetworkDevice` entities from the database.
     *
     * @return A `Flow` emitting a list of all `NetworkDevice` entities.
     */
    @Query("SELECT * FROM network_devices")
    fun getAllDevices(): Flow<List<NetworkDevice>>

    /**
     * Updates the online status of a `NetworkDevice` identified by its MAC address.
     *
     * @param macAddress The MAC address of the device to update.
     * @param isOnline The new online status of the device.
     */
    @Query("UPDATE network_devices SET onlineStatus = :isOnline WHERE macAddress = :macAddress")
    suspend fun updateDeviceOnlineStatus(macAddress: String, isOnline: Boolean)

    /**
     * Deletes all `NetworkDevice` entities from the database.
     */
    @Query("DELETE FROM network_devices")
    fun deleteAllDevices()

    /**
     * Retrieves the online status of a `NetworkDevice` identified by its MAC address.
     *
     * @param macAddress The MAC address of the device.
     * @return A `Flow` emitting the online status of the device.
     */
    @Query("SELECT onlineStatus FROM network_devices WHERE macAddress = :macAddress")
    fun getDeviceOnlineStatus(macAddress: String): Flow<Boolean>

    /**
     * Retrieves a `NetworkDevice` entity identified by its MAC address.
     *
     * @param macAddress The MAC address of the device.
     * @return A `Flow` emitting the `NetworkDevice` entity.
     */
    @Query("SELECT * FROM network_devices WHERE macAddress = :macAddress")
    fun getDevice(macAddress: String): Flow<NetworkDevice>
}



