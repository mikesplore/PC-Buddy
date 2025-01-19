package com.mike.vendor.model.users

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class UserRepository(
    private val userDao: UserDao,

) {
    private val firebaseDataSource=  FirebaseUserDataSource()

    fun insertUser(user: User): Flow<Result<String>> = flow {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
        firebaseDataSource.insertUser(user).collect { result ->
            emit(result)
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun getAllUsers(): Flow<Result<List<User>>> = flow {
        firebaseDataSource.getAllUsers().collect { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { users ->
                    withContext(Dispatchers.IO) {
                        users.forEach {
                            userDao.insertUser(it)
                        }
                    }
                }
            }
            emit(result)
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun getUserById(id: String): Flow<Result<User>> = flow {
        firebaseDataSource.getUserById(id).collect { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { user ->
                    withContext(Dispatchers.IO) {
                        userDao.insertUser(user)
                    }
                }
            }
            emit(result)
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun updateUser(user: User): Flow<Result<String>> = flow {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
        firebaseDataSource.updateUser(user).collect { result ->
            emit(result)
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun deleteUserById(id: String): Flow<Result<String>> = flow {
        withContext(Dispatchers.IO) {
            userDao.deleteUserById(id)
        }
        firebaseDataSource.deleteUserById(id).collect { result ->
            emit(result)
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}