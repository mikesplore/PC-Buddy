package com.mike.pcbuddy.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.pcbuddy.model.Server
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing and managing `Server` entities in the database.
 */
@Dao
interface ServerDao {

    /**
     * Inserts a `Server` into the database.
     * If the server already exists, it will be replaced.
     *
     * @param server The `Server` to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertServer(server: Server)

    /**
     * Retrieves all `Server` entities from the database.
     *
     * @return A `Flow` emitting a list of all `Server` entities.
     */
    @Query("SELECT * FROM servers")
    fun getAllServers(): Flow<List<Server>>

    /**
     * Updates the online status of a `Server` identified by its MAC address.
     *
     * @param macAddress The MAC address of the server to update.
     * @param isOnline The new online status of the server.
     */
    @Query("UPDATE servers SET onlineStatus = :isOnline WHERE macAddress = :macAddress")
    fun updateServerOnlineStatus(macAddress: String, isOnline: Boolean)

    /**
     * Updates all the `Server` entities in the database.
     * If a server does not exist, it will be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateServer(server: Server)

    /**
     * Deletes all `Server` entities from the database.
     */
    @Query("DELETE FROM servers")
    fun deleteAllServers()

    /**
     * Retrieves the online status of a `Server` identified by its MAC address.
     *
     * @param macAddress The MAC address of the server.
     * @return A `Flow` emitting the online status of the server.
     */
    @Query("SELECT onlineStatus FROM servers WHERE macAddress = :macAddress")
    fun getServerOnlineStatus(macAddress: String): Flow<Boolean>

    /**
     * Retrieves a `Server` entity identified by its MAC address.
     *
     * @param macAddress The MAC address of the server.
     * @return A `Flow` emitting the `Server` entity.
     */
    @Query("SELECT * FROM servers WHERE macAddress = :macAddress")
    fun getServer(macAddress: String): Flow<Server>



    /**
     * Deletes a `Server` entity identified by its MAC address.
     *
     * @param macAddress The MAC address of the server to delete.
     */
    @Query("DELETE FROM servers WHERE macAddress = :macAddress")
    fun deleteServer(macAddress: String)
}



