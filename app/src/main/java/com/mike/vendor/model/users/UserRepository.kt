package com.mike.vendor.model.users

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    fun insertUser(user: User): Flow<Result<String>> = flow {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
        emit(Result.success("User inserted successfully"))
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun getAllUsers(): Flow<Result<List<User>>> =
        userDao.getAllUsers()
            .map { Result.success(it) }
            .catch { exception ->
                emit(Result.failure(exception))
            }


    fun getUserById(id: String): Flow<Result<User>> =
        userDao.getUserById(id)
            .map { Result.success(it) }
            .catch { exception ->
                emit(Result.failure(exception))
            }

    fun updateUser(user: User): Flow<Result<String>> = flow {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
        emit(Result.success("User updated successfully"))
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun deleteUserById(id: String): Flow<Result<String>> = flow {
        withContext(Dispatchers.IO) {
            userDao.deleteUserById(id)
        }
        emit(Result.success("User deleted successfully"))
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}