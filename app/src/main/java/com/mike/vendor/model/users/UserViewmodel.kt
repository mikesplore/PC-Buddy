package com.mike.vendor.model.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    fun insertUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(user).onEach { result ->
                result.onSuccess {
                    onSuccess()
                }
                result.onFailure {
                    onError(it.message ?: "An error occurred")
                }
            }.launchIn(viewModelScope)
        }

    }

    //get all users
    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getAllUsers().onEach { result ->
                result.onSuccess {
                    _users.value = it
                }
                result.onFailure {
                    //handle error
                    Log.d("UserViewmodel", "getAllUsers: ${it.message}")
                }
            }.launchIn(viewModelScope)
        }
    }

    //get user by id
    fun getUserById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserById(id).onEach { result ->
                result.onSuccess {
                    _user.value = it
                }
                result.onFailure {
                    //handle error
                    Log.d("UserViewmodel", "getUserById: ${it.message}")
                }
            }.launchIn(viewModelScope)
        }
    }

    //update user
    fun updateUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user).onEach { result ->
                result.onSuccess {
                    onSuccess()
                }
                result.onFailure {
                    onError(it.message ?: "An error occurred")
                }
            }.launchIn(viewModelScope)
        }
    }

    //delete user
    fun deleteUserById(id: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUserById(id).onEach { result ->
                result.onSuccess {
                    onSuccess()
                }
                result.onFailure {
                    onError(it.message ?: "An error occurred")
                }
            }.launchIn(viewModelScope)
        }
    }
}