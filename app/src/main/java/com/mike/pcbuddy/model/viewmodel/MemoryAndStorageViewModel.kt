package com.mike.pcbuddy.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.pcbuddy.model.dataClasses.MemoryDetails
import com.mike.pcbuddy.model.dataClasses.StorageInfo
import com.mike.pcbuddy.model.repositories.MemoryAndStorageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryAndStorageViewModel @Inject constructor(private val memoryAndStorageRepo: MemoryAndStorageRepo) : ViewModel() {
    private val _memoryDetails = MutableStateFlow(MemoryDetails())
    val memoryDetails = _memoryDetails.asStateFlow()

    private val _storageInfo = MutableStateFlow(StorageInfo())
    val storageInfo = _storageInfo.asStateFlow()

    fun insertMemoryDetails(memoryDetails: MemoryDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            memoryAndStorageRepo.insertMemoryDetails(memoryDetails)
        }
    }

    fun insertStorageInfo(storageInfo: StorageInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            memoryAndStorageRepo.insertStorageInfo(storageInfo)
        }
    }

    fun getMemoryDetails(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _memoryDetails.value = memoryAndStorageRepo.getMemoryDetails(macAddress)
        }
    }

    fun getStorageInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _storageInfo.value = memoryAndStorageRepo.getStorageInfo(macAddress)
        }
    }

    fun deleteMemoryDetails(macAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            memoryAndStorageRepo.deleteMemoryDetails(macAddress)
        }
    }

}