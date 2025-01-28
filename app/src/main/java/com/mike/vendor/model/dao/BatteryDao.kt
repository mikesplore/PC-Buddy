package com.mike.vendor.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.vendor.model.dataClasses.BatteryDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface BatteryDao {
    @Query("SELECT * FROM battery where macAddress = :macAddress")
     fun getBatteryDetails(macAddress: String): Flow<BatteryDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatteryDetails(batteryDetails: BatteryDetails)

    @Query("DELETE FROM battery")
    suspend fun deleteAllBatteryDetails()

}