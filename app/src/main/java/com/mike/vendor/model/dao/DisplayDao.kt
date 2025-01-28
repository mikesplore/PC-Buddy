package com.mike.vendor.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.vendor.model.dataClasses.DisplayInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface DisplayDao {
    @Query("SELECT * FROM display_info WHERE macAddress = :macAddress")
    suspend fun getDisplayInfo(macAddress: String): Flow<List<DisplayInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisplayInfo(displayInfo: DisplayInfo)

    @Query("DELETE FROM display_info")
    suspend fun deleteDisplayInfo()


}