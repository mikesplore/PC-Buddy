package com.mike.vendor.model.repositories

import com.mike.vendor.model.dao.BatteryDao
import com.mike.vendor.model.dataClasses.BatteryDetails
import javax.inject.Inject

class BatteryRepository @Inject constructor(private val batteryDao: BatteryDao) {
     fun getBatteryDetails(macAddress: String) = batteryDao.getBatteryDetails(macAddress)
    suspend fun insertBatteryDetails(batteryDetails: BatteryDetails) = batteryDao.insertBatteryDetails(batteryDetails)
    suspend fun deleteAllBatteryDetails() = batteryDao.deleteAllBatteryDetails()

}