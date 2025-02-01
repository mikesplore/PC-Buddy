package com.mike.vendor.model

import android.content.Context
import androidx.room.Room
import com.mike.vendor.model.dao.BatteryDao
import com.mike.vendor.model.dao.DisplayDao
import com.mike.vendor.model.dao.MemoryAndStorageDao
import com.mike.vendor.model.dao.ServerDao
import com.mike.vendor.model.dao.SystemInfoDao
import com.mike.vendor.model.repositories.DisplayRepository
import com.mike.vendor.model.repositories.SystemInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Vendor_Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeviceDao(database: AppDatabase): ServerDao {
        return database.serverDao()
    }

    @Provides
    @Singleton
    fun provideBatteryDao(database: AppDatabase): BatteryDao {
        return database.batteryDao()
    }

    @Provides
    @Singleton
    fun provideMemoryAndStorageDao(database: AppDatabase): MemoryAndStorageDao {
        return database.memoryAndStorageDao()
    }

    @Provides
    @Singleton
    fun provideDisplayDao(database: AppDatabase): DisplayDao {
        return database.displayDao()
    }

    @Provides
    @Singleton
    fun provideDisplayRepository(displayDao: DisplayDao): DisplayRepository {
        return DisplayRepository(displayDao)
    }

    @Provides
    @Singleton
    fun provideSystemInfoDao(database: AppDatabase): SystemInfoDao {
        return database.systemInfoDao()
    }

    @Singleton
    @Provides
    fun provideSystemInfoRepository(systemInfoDao: SystemInfoDao): SystemInfoRepository {
        return SystemInfoRepository(systemInfoDao)
    }


}