package com.mike.vendor.model.repositories

import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.model.dao.DeviceDao
import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val deviceDao: DeviceDao) {

     fun insertDevice(device: NetworkDevice) {
        deviceDao.insertDevice(device)
    }

     fun getAllDevices(): Flow<List<NetworkDevice>> {
        return deviceDao.getAllDevices()
    }

    fun getDeviceOnlineStatus(macAddress: String): Flow<Boolean> {
        return deviceDao.getDeviceOnlineStatus(macAddress)
    }

    fun getDevice(macAddress: String): Flow<NetworkDevice> {
        return deviceDao.getDevice(macAddress)
    }


}