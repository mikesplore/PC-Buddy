package com.mike.vendor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servers")
data class Server(
    @PrimaryKey val macAddress: String,
    val name: String,
    val host: String,
    val port: Int,
    var onlineStatus: Boolean = true
){
    constructor(): this("", "", "", 0)
}