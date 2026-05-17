package com.example.roomdbkotlin.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  val name:String,
    val phonenumber: String? = null)
