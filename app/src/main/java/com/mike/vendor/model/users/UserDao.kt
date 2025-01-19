package com.mike.vendor.model.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: String): Flow<User>

    @Update(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun updateUser(user: User)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteUserById(id: String)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertUser(user: User)
}