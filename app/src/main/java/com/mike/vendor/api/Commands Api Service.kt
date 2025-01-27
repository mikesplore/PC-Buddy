package com.mike.vendor.api


import retrofit2.Call
import retrofit2.http.POST

interface CommandApiService {
    @POST("ping")
    fun ping(): Call<Void>

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

}