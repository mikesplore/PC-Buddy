package com.mike.vendor.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mike.vendor.model.dataClasses.BatteryDetails
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