package com.mike.pcbuddy.networkManager

import android.util.Log
import com.mike.pcbuddy.model.Server
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketTimeoutException
import java.net.BindException

private var receiveSocket: DatagramSocket? = null

/**
 * Discovers servers on the network using UDP packets.
 *
 * @param timeoutMillis The duration in milliseconds to keep discovering servers.
 * @return A Flow that emits discovered Server objects.
 */
fun discoverServers(
    timeoutMillis: Long = 10000
): Flow<Server> = flow {
    Log.d("ServerDiscovery", "Starting server discovery using Flow")

    try {
        // Close the existing socket if it's already open
        if (receiveSocket != null && !receiveSocket!!.isClosed) {
            receiveSocket?.close()
            receiveSocket = null
        }

        // Create a new socket
        receiveSocket = DatagramSocket(9999).apply {
            soTimeout = 1000 // Shorter timeout for individual packets
        }

        val buffer = ByteArray(1024)
        val packet = DatagramPacket(buffer, buffer.size)

        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            try {
                // Receive a packet
                receiveSocket?.receive(packet)
                val message = String(packet.data, 0, packet.length)

                // Check if the message starts with "SERVER_DISCOVERY:"
                if (message.startsWith("SERVER_DISCOVERY:")) {
                    val serverDetails = message.split(":")
                    if (serverDetails.size >= 7) {
                        val serverInfo = Server(
                            macAddress = serverDetails.slice(2..7).joinToString(":"),
                            name = serverDetails[1],
                            host = packet.address.hostAddress ?: "Unknown",
                            port = serverDetails[8].toIntOrNull() ?: 8080,
                            deviceType = serverDetails[1],
                            operatingSystem = serverDetails[9],
                            username = serverDetails[10]
                        )

                        Log.d("ServerDiscovery", "Discovered: $serverInfo")
                        emit(serverInfo)
                    }
                }
            } catch (e: SocketTimeoutException) {
                // Continue loop on timeout
                continue
            }
        }
    } catch (e: BindException) {
        Log.e("ServerDiscovery", "Socket binding error: ${e.message}")
    } catch (e: Exception) {
        Log.e("ServerDiscovery", "Discovery error: ${e.message}")
    } finally {
        // Close the socket
        receiveSocket?.close()
        receiveSocket = null
    }
}.flowOn(Dispatchers.IO)