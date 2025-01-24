package com.mike.vendor.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.vendor.model.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApp(app: AppEntity)

    @Query("SELECT * FROM apps ")
    fun getAllApps(): Flow<List<AppEntity>>

    @Query("DELETE FROM apps")
    fun clearApps()
}