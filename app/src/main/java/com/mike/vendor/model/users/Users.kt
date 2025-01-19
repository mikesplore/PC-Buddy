package com.mike.vendor.model.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val userType: UserType

){
    constructor(): this("","","","","",UserType.CUSTOMER)
}

enum class UserType {
    CUSTOMER,VENDOR
}