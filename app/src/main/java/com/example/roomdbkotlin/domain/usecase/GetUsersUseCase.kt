package com.example.roomdbkotlin.domain.usecase

import com.example.roomdbkotlin.data.local.UserEntity
import com.example.roomdbkotlin.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke() =
        repository.getUsers()
}

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(user: UserEntity) {
        repository.insert(user)
    }
}

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(user: UserEntity) {
        repository.delete(user)
    }
}

class SyncUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke() {
        repository.syncUsers()
    }
}


class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        user: UserEntity
    ) {

        repository.update(user)
    }
}