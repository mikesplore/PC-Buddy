package com.mike.pcbuddy.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.pcbuddy.model.dataClasses.DisplayInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface DisplayDao {
    @Query("SELECT * FROM display_info WHERE macAddress = :macAddress")
     fun getDisplayInfo(macAddress: String): Flow<List<DisplayInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertDisplayInfo(displayInfo: DisplayInfo)

    @Query("DELETE FROM display_info WHERE macAddress = :macAddress")
     fun deleteDisplayInfo(macAddress: String)

}