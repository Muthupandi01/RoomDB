package com.example.roomdbkotlin.domain.repository

import com.example.roomdbkotlin.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<UserEntity>>

    suspend fun insert(user: UserEntity)

    suspend fun delete(user: UserEntity)

    suspend fun update(user: UserEntity)

    suspend fun syncUsers()
}