package com.mike.vendor.model.repositories

import com.mike.vendor.model.AppEntity
import com.mike.vendor.model.dao.AppDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class AppRepository(private val appDao: AppDao) {

    fun insertApp(app: AppEntity) {
        appDao.insertApp(app)
    }

    fun getApps(): Flow<List<AppEntity>> = flow {
        emitAll(appDao.getAllApps())
    }

    fun clearApps() {
        appDao.clearApps()
    }
}