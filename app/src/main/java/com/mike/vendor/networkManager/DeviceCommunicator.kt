package com.mike.vendor.networkManager

import android.util.Log
import com.mike.vendor.model.NetworkDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class DeviceCommunicator @Inject constructor() {
    private val client = OkHttpClient()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun sendCommand(device: NetworkDevice, command: String) {
        val request = Request.Builder()
            .url("http://${device.host}:${device.port}/$command")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Network", "Failed to send command", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Network", "Command sent successfully: ${response.code}")
            }
        })
    }

    fun isDeviceOnline(device: NetworkDevice, callback: (Boolean) -> Unit) {
        Log.d("DeviceCommunicator", "Checking online status for device: ${device.name}")
        val host = device.host.trim('/', '[', ']')
        val port = device.port

        if (host.isEmpty() || !host.matches(Regex("^[a-zA-Z0-9.-]+$")) || port !in 1..65535) {
            callback(false)
            return
        }

        scope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://$host:$port/ping") // Ping endpoint
                Log.d("DeviceCommunicator", "Pinging URL: $url")
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 2000
                connection.readTimeout = 2000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                Log.d("DeviceCommunicator", "Ping response code: $responseCode")

                connection.disconnect()

                withContext(Dispatchers.Main) {
                    callback(responseCode == 200)
                }
            } catch (e: IOException) {
                Log.e("DeviceCommunicator", "Failed to ping URL: $host:$port/ping is unreachable", e)
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }
}
