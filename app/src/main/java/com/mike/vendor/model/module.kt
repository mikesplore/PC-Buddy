package com.mike.vendor.model

import android.content.Context
import androidx.room.Room
import com.mike.vendor.model.dao.ServerDao
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
            "servers_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeviceDao(database: AppDatabase): ServerDao {
        return database.serverDao()
    }
}