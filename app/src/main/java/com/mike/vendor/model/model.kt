package com.mike.vendor.model


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "network_devices_db"
        ).build()
    }

    @Provides
    fun provideDeviceDao(database: AppDatabase): DeviceDao {
        return database.deviceDao()
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(deviceDao: DeviceDao): DeviceRepository {
        return DeviceRepository(deviceDao)
    }
}
