package com.mike.vendor.model.repositories

import android.util.Log
import com.mike.vendor.model.Server
import com.mike.vendor.model.dao.ServerDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServerRepository @Inject constructor(private val serverDao: ServerDao) {

     fun insertServer(server: Server) {
        serverDao.insertServer(server)
    }

     fun getAllServers(): Flow<List<Server>> {
        return serverDao.getAllServers()
    }

    fun updateServer(server: Server){
        serverDao.updateServer(server)
    }

    fun getServerOnlineStatus(macAddress: String): Flow<Boolean> {
        return serverDao.getServerOnlineStatus(macAddress)
    }

    fun getServer(macAddress: String): Flow<Server> {
        val server = serverDao.getServer(macAddress)
        Log.d("ServerRepository", "Fetched server: $server")
        return server

    }


}