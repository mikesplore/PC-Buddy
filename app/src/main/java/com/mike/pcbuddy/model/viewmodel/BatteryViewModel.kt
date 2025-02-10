package com.mike.pcbuddy.model.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.pcbuddy.model.dataClasses.BatteryDetails
import com.mike.pcbuddy.model.repositories.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor(private val batteryRepository: BatteryRepository) :
    ViewModel() {
    private val _batteryDetails = MutableStateFlow<BatteryDetails?>(null)
    val batteryDetails = _batteryDetails.asStateFlow()

    fun getBatteryDetails(macAddress: String) {
        Log.d("BatteryViewModel", "Fetching battery details for $macAddress")
        viewModelScope.launch(Dispatchers.Main) {
            batteryRepository.getBatteryDetails(macAddress).collect { batteryDetails ->
                Log.d("BatteryViewModel", "Battery details fetched: $batteryDetails")
                _batteryDetails.value = batteryDetails
            }
        }
    }

    fun insertBatteryDetails(batteryDetails: BatteryDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            batteryRepository.insertBatteryDetails(batteryDetails)
        }
    }

    fun updateBatteryDetails(batteryDetails: BatteryDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            batteryRepository.insertBatteryDetails(batteryDetails)
        }
    }

    fun deleteAllBatteryDetails(macAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            batteryRepository.deleteAllBatteryDetails(macAddress)
        }
    }

}