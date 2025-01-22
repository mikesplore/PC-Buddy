package com.mike.vendor.model

import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val deviceDao: DeviceDao) {

    suspend fun insertDevice(device: NetworkDevice) {
        deviceDao.insertDevice(device)
    }

    suspend fun getAllDevices(): Flow<List<NetworkDevice>> {
        return deviceDao.getAllDevices()
    }

    suspend fun getDeviceById(id: Long): NetworkDevice? {
        return deviceDao.getDeviceById(id)
    }
}