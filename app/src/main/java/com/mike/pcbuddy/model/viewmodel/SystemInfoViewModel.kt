package com.mike.pcbuddy.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.pcbuddy.model.dataClasses.ComputerSystemDetails
import com.mike.pcbuddy.model.dataClasses.OperatingSystemInfo
import com.mike.pcbuddy.model.dataClasses.ProcessorDetails
import com.mike.pcbuddy.model.dataClasses.SoftwareInfo
import com.mike.pcbuddy.model.dataClasses.UserEnvironmentInfo
import com.mike.pcbuddy.model.repositories.SystemInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SystemInfoViewModel @Inject constructor(private val systemInfoRepository: SystemInfoRepository) :
    ViewModel() {

    private val _processorDetails = MutableStateFlow<ProcessorDetails?>(null)
    val processorDetails = _processorDetails.asStateFlow()

    private val _computerSystemDetails = MutableStateFlow<ComputerSystemDetails?>(null)
    val computerSystemDetails = _computerSystemDetails.asStateFlow()

    private val _operatingSystemInfo = MutableStateFlow<OperatingSystemInfo?>(null)
    val operatingSystemInfo = _operatingSystemInfo.asStateFlow()

    private val _softwareInfo = MutableStateFlow<SoftwareInfo?>(null)
    val softwareInfo = _softwareInfo.asStateFlow()

    private val _userEnvironmentInfo = MutableStateFlow<UserEnvironmentInfo?>(null)
    val userEnvironmentInfo = _userEnvironmentInfo.asStateFlow()

    fun insertAllSystemInfo(
        processorDetails: ProcessorDetails,
        computerSystemDetails: ComputerSystemDetails,
        operatingSystemInfo: OperatingSystemInfo,
        softwareInfo: SoftwareInfo,
        userEnvironmentInfo: UserEnvironmentInfo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            systemInfoRepository.insertSoftwareInfo(softwareInfo)
            systemInfoRepository.insertProcessorDetails(processorDetails)
            systemInfoRepository.insertComputerSystemDetails(computerSystemDetails)
            systemInfoRepository.insertOperatingSystemInfo(operatingSystemInfo)
            systemInfoRepository.insertUserEnvironmentInfo(userEnvironmentInfo)

        }
    }

    fun getProcessorDetails(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            systemInfoRepository.getProcessorDetails(macAddress).collect {
                _processorDetails.value = it

            }
        }
    }

    fun getComputerSystemDetails(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            systemInfoRepository.getComputerSystemDetails(macAddress).collect {
                _computerSystemDetails.value = it

            }
        }
    }

    fun getOperatingSystemInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            systemInfoRepository.getOperatingSystemInfo(macAddress).collect {
                _operatingSystemInfo.value = it

            }
        }
    }

    fun getSoftwareInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            systemInfoRepository.getSoftwareInfo(macAddress).collect {
                _softwareInfo.value = it

            }
        }
    }

    fun getUserEnvironmentInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            systemInfoRepository.getUserEnvironmentInfo(macAddress).collect {
                _userEnvironmentInfo.value = it

            }
        }
    }

    fun insertAllInfo(
        processorDetails: ProcessorDetails?,
        computerSystemDetails: ComputerSystemDetails?,
        operatingSystemInfo: OperatingSystemInfo?,
        softwareInfo: SoftwareInfo?,
        userEnvironmentInfo: UserEnvironmentInfo?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            softwareInfo?.let { systemInfoRepository.insertSoftwareInfo(it) }
            processorDetails?.let { systemInfoRepository.insertProcessorDetails(it) }
            computerSystemDetails?.let { systemInfoRepository.insertComputerSystemDetails(it) }
            operatingSystemInfo?.let { systemInfoRepository.insertOperatingSystemInfo(it) }
            userEnvironmentInfo?.let { systemInfoRepository.insertUserEnvironmentInfo(it) }

        }
    }

    fun getSystemInfo(macAddress: String) {
        viewModelScope.launch(Dispatchers.Main) {
            getOperatingSystemInfo(macAddress)
            getSoftwareInfo(macAddress)
            getProcessorDetails(macAddress)
            getComputerSystemDetails(macAddress)
            getUserEnvironmentInfo(macAddress)
        }
    }


}

