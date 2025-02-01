package com.mike.vendor.model.repositories

import com.mike.vendor.model.dao.DisplayDao
import com.mike.vendor.model.dataClasses.DisplayInfo
import javax.inject.Inject

class DisplayRepository @Inject constructor(private val displayDao: DisplayDao){
    fun getDisplay(macAddress: String) = displayDao.getDisplayInfo(macAddress)
     fun insertDisplay(display: DisplayInfo) = displayDao.insertDisplayInfo(display)
     fun deleteDisplay(macAddress: String) = displayDao.deleteDisplayInfo(macAddress)

}