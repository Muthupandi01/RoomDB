package com.example.roomdbkotlin.presentation.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdbkotlin.data.local.UserEntity
import com.example.roomdbkotlin.data.utils.Resource
import com.example.roomdbkotlin.domain.repository.UserRepository
import com.example.roomdbkotlin.domain.usecase.DeleteUserUseCase
import com.example.roomdbkotlin.domain.usecase.GetUsersUseCase
import com.example.roomdbkotlin.domain.usecase.InsertUserUseCase
import com.example.roomdbkotlin.domain.usecase.SyncUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val syncUsersUseCase: SyncUsersUseCase,
    private val repository: UserRepository
) : ViewModel() {


    private val _usersState =
        MutableStateFlow<Resource<List<UserEntity>>>(
            Resource.Loading()
        )

    val usersState =
        _usersState.asStateFlow()

    val users = getUsersUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        getUsers()
        viewModelScope.launch {
            syncUsersUseCase()
        }
    }

    fun insert(name: String, phonenumber: String) {

        viewModelScope.launch {

            insertUserUseCase(
                UserEntity(name = name, phonenumber = phonenumber)
            )
        }
    }

    fun delete(user: UserEntity) {

        viewModelScope.launch {

            deleteUserUseCase(user)
        }
    }

    fun update(
        id: Int,
        name: String,
        phonenumber: String
    ) {

        viewModelScope.launch {

            repository.update(
                UserEntity(
                    id = id,
                    name = name,
                    phonenumber = phonenumber
                )
            )
        }
    }

    private fun getUsers() {

        viewModelScope.launch {

            _usersState.value = Resource.Loading()

            try {

                getUsersUseCase().collect {

                    _usersState.value =
                        Resource.Success(it)
                }

            } catch (e: Exception) {

                _usersState.value =
                    Resource.Error(
                        e.message ?: "Unknown Error"
                    )
            }
        }
    }
}