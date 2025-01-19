package com.mike.vendor.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.vendor.model.users.User
import com.mike.vendor.model.users.UserDao


@Database(
    entities = [
        User::class,

    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class VendorDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


}

