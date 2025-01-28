package com.mike.vendor.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.StorageInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun sendCommand(commandName: String, context: Context, ipAddress: String, port: Int) {
    val command = commands.find { it.name == commandName } ?: return
    val baseUrl = "http://$ipAddress:$port"
    val apiService = RetrofitClient.create(baseUrl)
    val call: Call<Void> = command.execute(apiService)

    call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Toast.makeText(context, "$commandName command sent successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Failed to send $commandName command", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

fun fetchBatteryInfo(
    ipAddress: String,
    port: Int,
    onSuccess: (BatteryDetails) -> Unit,
    onFailure: (String) -> Unit
) {
    val baseUrl = "http://$ipAddress:$port"
    val apiService = RetrofitClient.create(baseUrl)
    val call: Call<BatteryDetails> = apiService.getBattery()

    call.enqueue(object : Callback<BatteryDetails> {
        override fun onResponse(call: Call<BatteryDetails>, response: Response<BatteryDetails>) {
            if (response.isSuccessful) {
                Log.d("fetchBatteryInfo", "Response successful")
                response.body()?.let { data ->
                    onSuccess(data)
                    Log.d("fetchBatteryInfo", "Data: $data")
                } ?: run {
                    onFailure("Failed to fetch battery info: Empty response")
                    Log.d("fetchBatteryInfo", "Empty response")
                }
            } else {
                Log.d("fetchBatteryInfo", "Failed to fetch battery info: ${response.message()}")
                onFailure("Failed to fetch battery info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<BatteryDetails>, t: Throwable) {
            onFailure("Error: ${t.message}")
        }
    })
}


fun fetchMemoryInfo(
    ipAddress: String,
    port: Int,
    onSuccess: (MemoryDetails) -> Unit,
    onFailure: (String) -> Unit
) {
    val baseUrl = "http://$ipAddress:$port"
    val apiService = RetrofitClient.create(baseUrl)
    val call: Call<MemoryDetails> = apiService.getMemory()

    call.enqueue(object : Callback<MemoryDetails> {
        override fun onResponse(call: Call<MemoryDetails>, response: Response<MemoryDetails>) {
            if (response.isSuccessful) {
                Log.d("fetchMemoryInfo", "Response successful")
                response.body()?.let { data ->
                    onSuccess(data)
                    Log.d("fetchMemoryInfo", "Data: $data")
                } ?: run {
                    onFailure("Failed to fetch memory info: Empty response")
                    Log.d("fetchMemoryInfo", "Empty response")
                }
            } else {
                Log.d("fetchMemoryInfo", "Failed to fetch memory info: ${response.message()}")
                onFailure("Failed to fetch memory info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<MemoryDetails>, t: Throwable) {
            onFailure("Error: ${t.message}")
        }
    })
}

fun fetchStorageInfo(
    ipAddress: String,
    port: Int,
    onSuccess: (StorageInfo) -> Unit,
    onFailure: (String) -> Unit
) {
    val baseUrl = "http://$ipAddress:$port"
    val apiService = RetrofitClient.create(baseUrl)
    val call: Call<StorageInfo> = apiService.getStorage()

    call.enqueue(object : Callback<StorageInfo> {
        override fun onResponse(call: Call<StorageInfo>, response: Response<StorageInfo>) {
            if (response.isSuccessful) {
                Log.d("fetchStorageInfo", "Response successful")
                response.body()?.let { data ->
                    onSuccess(data)
                    Log.d("fetchStorageInfo", "Data: $data")
                } ?: run {
                    onFailure("Failed to fetch storage info: Empty response")
                    Log.d("fetchStorageInfo", "Empty response")
                }
            } else {
                Log.d("fetchStorageInfo", "Failed to fetch storage info: ${response.message()}")
                onFailure("Failed to fetch storage info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<StorageInfo>, t: Throwable) {
            onFailure("Error: ${t.message}")
        }
    })
}


fun fetchDisplayInfo(
    ipAddress: String,
    port: Int,
    onSuccess: (List<DisplayInfo>) -> Unit,
    onFailure: (String) -> Unit
) {
    val baseUrl = "http://$ipAddress:$port"
    val apiService = RetrofitClient.create(baseUrl)
    val call: Call<List<DisplayInfo>> = apiService.getDisplay()

    call.enqueue(object : Callback<List<DisplayInfo>> {
        override fun onResponse(call: Call<List<DisplayInfo>>, response: Response<List<DisplayInfo>>) {
            if (response.isSuccessful) {
                Log.d("fetchDisplayInfo", "Response successful")
                response.body()?.let { data ->
                    onSuccess(data)
                    Log.d("fetchDisplayInfo", "Data: $data")
                } ?: run {
                    onFailure("Failed to fetch display info: Empty response")
                    Log.d("fetchDisplayInfo", "Empty response")
                }
            } else {
                Log.d("fetchDisplayInfo", "Failed to fetch display info: ${response.message()}")
                onFailure("Failed to fetch display info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<List<DisplayInfo>>, t: Throwable) {
            onFailure("Error: ${t.message}")
        }
    })
}
