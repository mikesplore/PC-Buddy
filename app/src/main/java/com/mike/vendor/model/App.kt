package com.mike.vendor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class AppEntity(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val name: String,

){
    constructor(name: String) : this(0, name)
}
