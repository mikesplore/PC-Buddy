package com.mike.vendor.model.hilt

import android.content.Context
import androidx.room.Room
import com.mike.vendor.model.users.UserRepository
import com.mike.vendor.model.database.VendorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //Add your dependencies here
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VendorDatabase {
        return Room.databaseBuilder(
            context,
            VendorDatabase::class.java,
            "vendor_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: VendorDatabase): UserRepository {
        return UserRepository(database.userDao())
    }
}