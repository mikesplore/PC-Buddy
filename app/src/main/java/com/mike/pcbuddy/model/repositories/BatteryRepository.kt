package com.mike.pcbuddy.model.repositories

import com.mike.pcbuddy.model.dao.BatteryDao
import com.mike.pcbuddy.model.dataClasses.BatteryDetails
import javax.inject.Inject

class BatteryRepository @Inject constructor(private val batteryDao: BatteryDao) {
     fun getBatteryDetails(macAddress: String) = batteryDao.getBatteryDetails(macAddress)
    suspend fun insertBatteryDetails(batteryDetails: BatteryDetails) = batteryDao.insertBatteryDetails(batteryDetails)
    suspend fun deleteAllBatteryDetails(macAddress: String) = batteryDao.deleteAllBatteryDetails(macAddress)

}