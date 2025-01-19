package com.mike.vendor.model.users

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FirebaseUserDataSource {

    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun insertUser(user: User): Flow<Result<String>> = flow {
        try {
            usersCollection.document(user.id).set(user).await()
            emit(Result.success("User inserted successfully"))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun getAllUsers(): Flow<Result<List<User>>> = flow {
        try {
            val snapshot = usersCollection.get().await()
            val users = snapshot.toObjects(User::class.java)
            emit(Result.success(users))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun getUserById(id: String): Flow<Result<User>> = flow {
        try {
            val snapshot = usersCollection.document(id).get().await()
            val user = snapshot.toObject(User::class.java)
            if (user != null) {
                emit(Result.success(user))
            } else {
                emit(Result.failure(Exception("User not found")))
            }
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun updateUser(user: User): Flow<Result<String>> = flow {
        try {
            usersCollection.document(user.id).set(user).await()
            emit(Result.success("User updated successfully"))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    fun deleteUserById(id: String): Flow<Result<String>> = flow {
        try {
            usersCollection.document(id).delete().await()
            emit(Result.success("User deleted successfully"))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}