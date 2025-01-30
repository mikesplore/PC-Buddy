package com.mike.vendor.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.dataClasses.ComputerSystemDetails
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.OperatingSystemInfo
import com.mike.vendor.model.dataClasses.ProcessorDetails
import com.mike.vendor.model.dataClasses.SoftwareInfo
import com.mike.vendor.model.dataClasses.StorageInfo
import com.mike.vendor.model.dataClasses.SystemInfo
import com.mike.vendor.model.dataClasses.UserEnvironmentInfo
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
                Toast.makeText(
                    context,
                    "$commandName command sent successfully",
                    Toast.LENGTH_SHORT
                )
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
        override fun onResponse(
            call: Call<List<DisplayInfo>>,
            response: Response<List<DisplayInfo>>
        ) {
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


fun fetchSystemInfo(
    ipAddress: String,
    port: Int,
    onSuccess: (SystemInfo) -> Unit,
    onFailure: (String) -> Unit
) {
    val baseUrl = "http://$ipAddress:$port"
    val apiService = RetrofitClient.create(baseUrl)

    // Create Retrofit calls for all the data you want to fetch
    val processorCall: Call<ProcessorDetails> = apiService.getProcessor()
    val computerSystemCall: Call<ComputerSystemDetails> = apiService.getCurrentSystemInfo()
    val osInfoCall: Call<OperatingSystemInfo> = apiService.getOperatingSystemInfo()
    val softwareInfoCall: Call<SoftwareInfo> = apiService.getSoftware()
    val userEnvInfoCall: Call<UserEnvironmentInfo> = apiService.getUserEnvironmentInfo()

    // Create a list to collect all the successful responses
    val responses = mutableMapOf<String, Any>()

    // Check if all responses are received
    fun checkAllResponses(onSuccess: (SystemInfo) -> Unit, responses: MutableMap<String, Any>) {
        if (responses.size == 5) {
            val systemInfo = SystemInfo(
                responses["processorDetails"] as ProcessorDetails?,
                responses["computerSystemDetails"] as ComputerSystemDetails?,
                responses["operatingSystemInfo"] as OperatingSystemInfo?,
                responses["softwareInfo"] as SoftwareInfo?,
                responses["userEnvironmentInfo"] as UserEnvironmentInfo?
            )
            onSuccess(systemInfo)
        }
    }

    // Create separate callback functions for each API call
    val processorCallback = object : Callback<ProcessorDetails> {
        override fun onResponse(
            call: Call<ProcessorDetails>,
            response: Response<ProcessorDetails>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    responses["processorDetails"] = data
                    checkAllResponses(onSuccess, responses)
                } ?: onFailure("Failed to fetch processor details: Empty response")
            } else {
                onFailure("Failed to fetch processor details: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<ProcessorDetails>, t: Throwable) {
            onFailure("Error fetching processor details: ${t.message}")
        }
    }

    val computerSystemCallback = object : Callback<ComputerSystemDetails> {
        override fun onResponse(
            call: Call<ComputerSystemDetails>,
            response: Response<ComputerSystemDetails>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    responses["computerSystemDetails"] = data
                    checkAllResponses(onSuccess, responses)
                } ?: onFailure("Failed to fetch computer system details: Empty response")
            } else {
                onFailure("Failed to fetch computer system details: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<ComputerSystemDetails>, t: Throwable) {
            onFailure("Error fetching computer system details: ${t.message}")
        }
    }

    val osInfoCallback = object : Callback<OperatingSystemInfo> {
        override fun onResponse(
            call: Call<OperatingSystemInfo>,
            response: Response<OperatingSystemInfo>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    responses["operatingSystemInfo"] = data
                    checkAllResponses(onSuccess, responses)
                } ?: onFailure("Failed to fetch OS info: Empty response")
            } else {
                onFailure("Failed to fetch OS info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<OperatingSystemInfo>, t: Throwable) {
            onFailure("Error fetching OS info: ${t.message}")
        }
    }

    val softwareInfoCallback = object : Callback<SoftwareInfo> {
        override fun onResponse(call: Call<SoftwareInfo>, response: Response<SoftwareInfo>) {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    responses["softwareInfo"] = data
                    checkAllResponses(onSuccess, responses)
                } ?: onFailure("Failed to fetch software info: Empty response")
            } else {
                onFailure("Failed to fetch software info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<SoftwareInfo>, t: Throwable) {
            onFailure("Error fetching software info: ${t.message}")
        }
    }

    val userEnvInfoCallback = object : Callback<UserEnvironmentInfo> {
        override fun onResponse(
            call: Call<UserEnvironmentInfo>,
            response: Response<UserEnvironmentInfo>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    responses["userEnvironmentInfo"] = data
                    checkAllResponses(onSuccess, responses)
                } ?: onFailure("Failed to fetch user environment info: Empty response")
            } else {
                onFailure("Failed to fetch user environment info: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<UserEnvironmentInfo>, t: Throwable) {
            onFailure("Error fetching user environment info: ${t.message}")
        }
    }

    // Enqueue all the calls
    processorCall.enqueue(processorCallback)
    computerSystemCall.enqueue(computerSystemCallback)
    osInfoCall.enqueue(osInfoCallback)
    softwareInfoCall.enqueue(softwareInfoCallback)
    userEnvInfoCall.enqueue(userEnvInfoCallback)
}
