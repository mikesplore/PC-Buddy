package com.mike.vendor.model.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.vendor.model.Server
import com.mike.vendor.model.repositories.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerViewModel @Inject constructor(
    private val repository: ServerRepository
) : ViewModel() {
    private val _servers = MutableStateFlow<List<Server>>(emptyList())
    val servers = _servers.asStateFlow()

    private val _server = MutableStateFlow<Server?>(null)
    val server = _server.asStateFlow()

    private val _serverOnlineStatus = MutableStateFlow<Boolean?>(false)
    val serverOnlineStatus = _serverOnlineStatus.asStateFlow()

    fun insertServer(server: Server) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertServer(server)
        }
    }

    fun getAllServers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllServers().collect {
                _servers.value = it
            }
        }
    }

    fun getServerOnlineStatus(macAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getServerOnlineStatus(macAddress).collect { server ->
                _serverOnlineStatus.value = server
            }
        }
    }

    fun getServer(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) { // Use Dispatchers.Main
            repository.getServer(macAddress).collect { server ->
                _server.value = server
                Log.d("ServerViewModel", "Server got: $server")
            }
        }
    }

    fun updateServer(server: Server) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateServer(server)
        }
    }

}