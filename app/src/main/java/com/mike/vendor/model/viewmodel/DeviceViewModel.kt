package com.mike.vendor.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.model.repositories.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val repository: DeviceRepository
) : ViewModel() {
    private val _devices = MutableStateFlow<List<NetworkDevice>>(emptyList())
    val devices = _devices.asStateFlow()

    private val _device = MutableStateFlow<NetworkDevice?>(null)
    val device = _device.asStateFlow()

    private val _deviceOnlineStatus = MutableStateFlow<Boolean?>(false)
    val deviceOnlineStatus = _deviceOnlineStatus.asStateFlow()

    fun insertDevice(device: NetworkDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDevice(device)
        }
    }

    fun getAllDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDevices().collect {
                _devices.value = it
            }
        }
    }

    fun getDeviceOnlineStatus(macAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDeviceOnlineStatus(macAddress).collect { device ->
                _deviceOnlineStatus.value = device
            }
        }
    }

    fun getDevice(macAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDevice(macAddress).collect { device ->
                _devices.value = listOf(device)
            }
        }
    }

}