package com.mike.vendor.networkManager

import android.util.Log
import com.mike.vendor.model.Server
import com.mike.vendor.model.dao.ServerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Modified fetchServers function
fun fetchServers(
    serverDao: ServerDao,
    scope: CoroutineScope
) {
    Log.d("ServerDiscovery", "Starting fetchServers")

    scope.launch(Dispatchers.IO) {
        while (true) {
            try {
                // Create a mutable set to store newly discovered servers
                val discoveredServers = mutableSetOf<Server>()

                // Discover servers
                discoverServers().collect { server ->
                    Log.d("ServerDiscovery", "New server discovered: ${server.macAddress}")
                    discoveredServers.add(server)
                }

                if (discoveredServers.isEmpty()) {
                    // No discovered servers, set all database servers to offline
                    setAllServersOffline(serverDao)
                } else {
                    // Update database servers' status based on discovered servers
                    updateServerStatuses(serverDao, discoveredServers)

                    // Insert any new servers that aren't in the database
                    insertNewServers(serverDao, discoveredServers)
                }
            } catch (e: Exception) {
                Log.e("ServerDiscovery", "Error collecting servers: ${e.message}")
            }

            // Wait for a specified delay before the next check
            delay(10000) // 60 seconds delay
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

private suspend fun updateServerStatuses(
    serverDao: ServerDao,
    discoveredServers: Set<Server>
) {
    val dbServers = serverDao.getAllServers().first()

    // Create a set of discovered MAC addresses for efficient lookup
    val discoveredMacs = discoveredServers.map { it.macAddress }.toSet()

    // Update status for all database servers
    dbServers.forEach { dbServer ->
        val shouldBeOnline = discoveredMacs.contains(dbServer.macAddress)
        if (dbServer.onlineStatus != shouldBeOnline) {
            val updatedServer = dbServer.copy(onlineStatus = shouldBeOnline)
            serverDao.updateServer(updatedServer)
            Log.d(
                "ServerDiscovery",
                "Updated server ${dbServer.macAddress} status to ${if (shouldBeOnline) "online" else "offline"}"
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

    // Insert only servers that don't exist in database
    discoveredServers
        .filterNot { existingMacs.contains(it.macAddress) }
        .forEach { newServer ->
            serverDao.insertServer(newServer)
            Log.d("ServerDiscovery", "Inserted new server: ${newServer.macAddress}")
        }
}
