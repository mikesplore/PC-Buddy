package com.mike.vendor.networkManager

import android.util.Log
import com.mike.vendor.model.AppEntity
import com.mike.vendor.model.NetworkDevice
import com.mike.vendor.model.dao.AppDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class AppManager {
    private val client = OkHttpClient()
    private lateinit var appDao: AppDao
    fun getInstalledApps(device: NetworkDevice) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url("http://${device.host}:${device.port}/get_installed_apps")
                .build()

            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val apps = parseAppsFromJson(responseBody)
                    saveAppsToDatabase(apps)
                } else {
                    withContext(Dispatchers.Main) {
                    }
                }
            } catch (e: IOException) {
                Log.e("AppManager", "Failed to get installed apps", e)
            }
        }
    }

    private fun parseAppsFromJson(jsonString: String?): List<String> {
        if (jsonString.isNullOrEmpty()) return emptyList()
        val jsonObject = JSONObject(jsonString)
        val appsArray = jsonObject.getJSONArray("apps")
        val appsList = mutableListOf<String>()
        for (i in 0 until appsArray.length()) {
            appsList.add(appsArray.getString(i))
        }
        return appsList
    }

    private fun saveAppsToDatabase(apps: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            apps.forEach { appName ->
                val appEntity = AppEntity(name = appName)
                appDao.insertApp(appEntity)
            }
        }
    }

    fun openApp(device: NetworkDevice, appName: String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url("http://${device.host}:${device.port}/open_app?name=$appName")
                .build()

            try {
                val response = client.newCall(request).execute()
                withContext(Dispatchers.Main) {
                    callback(response.isSuccessful)
                }
            } catch (e: IOException) {
                Log.e("AppManager", "Failed to open app", e)
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }

    fun closeApp(device: NetworkDevice, appName: String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url("http://${device.host}:${device.port}/close_app?name=$appName")
                .build()

            try {
                val response = client.newCall(request).execute()
                withContext(Dispatchers.Main) {
                    callback(response.isSuccessful)
                }
            } catch (e: IOException) {
                Log.e("AppManager", "Failed to close app", e)
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }
}