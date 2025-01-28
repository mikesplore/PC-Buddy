package com.mike.vendor.networkManager

import android.util.Log
import com.mike.vendor.model.Server
import com.mike.vendor.model.dao.ServerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun fetchServers(
    serverDao: ServerDao,
    scope: CoroutineScope
) {
    Log.d("ServerDiscovery", "Starting fetchServers")

    scope.launch(Dispatchers.IO) {
        while (true) {
            try {
                val discoveredServers = mutableSetOf<Server>()

                // Discover servers
                discoverServers().collect { server ->
                    Log.d("ServerDiscovery", "New server discovered: ${server.macAddress} with host ${server.host}")
                    discoveredServers.add(server)
                }

                if (discoveredServers.isEmpty()) {
                    setAllServersOffline(serverDao)
                } else {
                    updateServerStatusesAndHosts(serverDao, discoveredServers)
                    insertNewServers(serverDao, discoveredServers)
                }
            } catch (e: Exception) {
                Log.e("ServerDiscovery", "Error collecting servers: ${e.message}")
            }

            delay(10000) // 10-second delay before the next check
        }
    }
}

private suspend fun setAllServersOffline(serverDao: ServerDao) {
    val dbServers = serverDao.getAllServers().first()
    dbServers.forEach { dbServer ->
        if (dbServer.onlineStatus) {
            val updatedServer = dbServer.copy(onlineStatus = false)
            serverDao.updateServer(updatedServer)
            Log.d("ServerDiscovery", "Set server ${dbServer.macAddress} status to offline")
        }
    }
}

private suspend fun updateServerStatusesAndHosts(
    serverDao: ServerDao,
    discoveredServers: Set<Server>
) {
    val dbServers = serverDao.getAllServers().first()
    val discoveredServerMap = discoveredServers.associateBy { it.macAddress }

    dbServers.forEach { dbServer ->
        val discoveredServer = discoveredServerMap[dbServer.macAddress]
        if (discoveredServer != null) {
            val shouldBeOnline = true
            val hostChanged = dbServer.host != discoveredServer.host

            if (dbServer.onlineStatus != shouldBeOnline || hostChanged) {
                val updatedServer = dbServer.copy(
                    onlineStatus = shouldBeOnline,
                    host = discoveredServer.host
                )
                serverDao.updateServer(updatedServer)
                Log.d(
                    "ServerDiscovery",
                    "Updated server ${dbServer.macAddress} status to online and host to ${discoveredServer.host}"
                )
            }
        } else if (dbServer.onlineStatus) {
            val updatedServer = dbServer.copy(onlineStatus = false)
            serverDao.updateServer(updatedServer)
            Log.d(
                "ServerDiscovery",
                "Set server ${dbServer.macAddress} status to offline"
            )
        }
    }
}

private suspend fun insertNewServers(
    serverDao: ServerDao,
    discoveredServers: Set<Server>
) {
    val dbServers = serverDao.getAllServers().first()
    val existingMacs = dbServers.map { it.macAddress }.toSet()

    discoveredServers
        .filterNot { existingMacs.contains(it.macAddress) }
        .forEach { newServer ->
            serverDao.insertServer(newServer)
            Log.d("ServerDiscovery", "Inserted new server: ${newServer.macAddress} with host ${newServer.host}")
        }
}
