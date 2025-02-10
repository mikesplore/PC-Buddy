package com.mike.pcbuddy.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.pcbuddy.model.dataClasses.DisplayInfo
import com.mike.pcbuddy.model.repositories.DisplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(private val displayRepository: DisplayRepository) :
    ViewModel() {
    private val _display = MutableStateFlow<List<DisplayInfo>?>(null)
    val display = _display.asStateFlow()

    fun getDisplayInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val displayInfo = displayRepository.getDisplay(macAddress)
            displayInfo.collect { displayInf ->
                _display.value = displayInf
            }
        }
    }

    fun insertDisplayInfo(displayInfo: DisplayInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            displayRepository.insertDisplay(displayInfo)
        }
    }

    fun updateDisplayInfo(displayInfo: DisplayInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            displayRepository.insertDisplay(displayInfo)
        }
    }

    fun deleteDisplayInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            displayRepository.deleteDisplay(macAddress)
        }
    }
}