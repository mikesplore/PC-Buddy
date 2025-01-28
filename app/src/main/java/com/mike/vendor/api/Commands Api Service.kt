package com.mike.vendor.api


import com.mike.vendor.model.dataClasses.BatteryDetails
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.dataClasses.MemoryDetails
import com.mike.vendor.model.dataClasses.StorageInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface CommandApiService {
    @POST("shutdown")
    fun shutdown(): Call<Void>

    @POST("restart")
    fun restart(): Call<Void>

    @POST("sleep")
    fun sleep(): Call<Void>

    @POST("hibernate")
    fun hibernate(): Call<Void>

    @POST("logoff")
    fun logoff(): Call<Void>

    @POST("lock")
    fun lock(): Call<Void>

    @GET("battery")
    fun getBattery(): Call<BatteryDetails>

    @GET("storage")
    fun getStorage(): Call<StorageInfo>

    @GET("memory")
    fun getMemory(): Call<MemoryDetails>

    @GET("displays")
    fun getDisplay(): Call<DisplayInfo>

}

