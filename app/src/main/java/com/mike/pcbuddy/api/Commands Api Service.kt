package com.mike.pcbuddy.api


import com.mike.pcbuddy.model.dataClasses.BatteryDetails
import com.mike.pcbuddy.model.dataClasses.ComputerSystemDetails
import com.mike.pcbuddy.model.dataClasses.DisplayInfo
import com.mike.pcbuddy.model.dataClasses.MemoryDetails
import com.mike.pcbuddy.model.dataClasses.OperatingSystemInfo
import com.mike.pcbuddy.model.dataClasses.ProcessorDetails
import com.mike.pcbuddy.model.dataClasses.SoftwareInfo
import com.mike.pcbuddy.model.dataClasses.StorageInfo
import com.mike.pcbuddy.model.dataClasses.UserEnvironmentInfo
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
    fun getDisplay(): Call<List<DisplayInfo>>


    @GET("softwareInfo")
    fun getSoftware(): Call<SoftwareInfo>

    @GET("processorDetails")
    fun getProcessor(): Call<ProcessorDetails>

    @GET("operatingSystemInfo")
    fun getOperatingSystemInfo(): Call<OperatingSystemInfo>

    @GET("userEnvironmentInfo")
    fun getUserEnvironmentInfo(): Call<UserEnvironmentInfo>

    @GET("systemInfo")
    fun getCurrentSystemInfo(): Call<ComputerSystemDetails>


}

