package com.mike.vendor.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.vendor.model.AppEntity
import com.mike.vendor.model.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _apps = MutableStateFlow<List<AppEntity>>(emptyList())
    val apps = _apps.asStateFlow()

    fun insertApp(app: AppEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.insertApp(app)
        }
    }

    fun getApps() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getApps().collect {
                _apps.value = it
            }
        }
    }

    fun clearApps() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.clearApps()
        }
    }
}