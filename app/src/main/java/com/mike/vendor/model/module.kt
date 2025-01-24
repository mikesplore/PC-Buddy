package com.mike.vendor.model

import android.content.Context
import android.net.nsd.NsdManager
import androidx.room.Room
import com.mike.vendor.model.dao.AppDao
import com.mike.vendor.model.dao.DeviceDao
import com.mike.vendor.model.repositories.AppRepository
import com.mike.vendor.model.repositories.DeviceRepository
import com.mike.vendor.networkManager.AppManager
import com.mike.vendor.networkManager.DeviceCommunicator
import com.mike.vendor.networkManager.NetworkManager
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
            "network_devices_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeviceDao(database: AppDatabase): DeviceDao {
        return database.deviceDao()
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(deviceDao: DeviceDao): DeviceRepository {
        return DeviceRepository(deviceDao)
    }

    @Provides
    @Singleton
    fun provideNsdManager(@ApplicationContext context: Context): NsdManager {
        return context.getSystemService(Context.NSD_SERVICE) as NsdManager
    }

    @Provides
    @Singleton
    fun provideNetworkManager(nsdManager: NsdManager): NetworkManager {
        return NetworkManager(nsdManager)
    }

    @Provides
    @Singleton
    fun provideAppManager(): AppManager {
        return AppManager()
    }

    @Provides
    @Singleton
    fun provideDeviceCommunicator(): DeviceCommunicator {
        return DeviceCommunicator()
    }

    @Provides
    @Singleton
    fun provideAppRepository(appDao: AppDao): AppRepository {
        return AppRepository(appDao)
    }

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): AppDao {
        return database.appDao()
    }
}