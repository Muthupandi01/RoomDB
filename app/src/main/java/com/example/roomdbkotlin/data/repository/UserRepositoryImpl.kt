package com.example.roomdbkotlin.data.repository

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.roomdbkotlin.data.local.UserDao
import com.example.roomdbkotlin.data.local.UserEntity
import com.example.roomdbkotlin.data.remote.ApiService
import com.example.roomdbkotlin.data.utils.NetworkUtils
import com.example.roomdbkotlin.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val api: ApiService,
    @ApplicationContext private val context: Context
) : UserRepository {

    override fun getUsers(): Flow<List<UserEntity>> {
        return dao.getUsers()
    }

    override suspend fun insert(user: UserEntity) {
        dao.insert(user)
    }

    override suspend fun delete(user: UserEntity) {
        dao.delete(user)
    }

    override suspend fun update(user: UserEntity) {
        dao.update(user)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun syncUsers() {

        if (NetworkUtils.isInternetAvailable(context)) {

            val apiUsers = api.getUsers()

            apiUsers.forEach {

                dao.insert(
                    UserEntity(
                        id = it.id,
                        name = it.name,
                        phonenumber = it.phonenumber,
                    )
                )
            }
        }
    }
}