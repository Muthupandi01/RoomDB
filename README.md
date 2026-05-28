<!--
    CLEAN ACRCHITECTURE

    com.example.userapp
    │
    ├── data
    │   ├── local
    │   │   ├── UserDao.kt
    │   │   ├── UserEntity.kt
    │   │   └── AppDatabase.kt
    │   │
    │   ├── remote
    │   │   ├── ApiService.kt
    │   │   └── UserDto.kt
    │   │
    │   ├── repository
    │   │   └── UserRepositoryImpl.kt
    │
    ├── domain
    │   ├── model
    │   │   └── User.kt
    │   │
    │   ├── repository
    │   │   └── UserRepository.kt
    │   │
    │   └── usecase
    │       ├── GetUsersUseCase.kt
    │       ├── InsertUserUseCase.kt
    │       ├── DeleteUserUseCase.kt
    │       └── SyncUsersUseCase.kt
    │
    ├── di
    │   └── AppModule.kt
    │
    ├── presentation
    │   ├── screen
    │   │   └── UserScreen.kt
    │   │
    │   └── viewmodel
    │       └── UserViewModel.kt
    │
    ├── utils
    │   └── NetworkUtils.kt
    │
    ├── MainActivity.kt
    └── MyApplication.kt-->



<!--    Offline Support CRUD App
    Features

    ✅ Jetpack Compose
    ✅ Clean Architecture
    ✅ MVVM
    ✅ Hilt Dependency Injection
    ✅ Room Database
    ✅ Retrofit API
    ✅ CRUD Operations
    ✅ Internet ON/OFF Handling
    ✅ Offline First App
    ✅ Flow + StateFlow
    ✅ Compose UI-->

POJECT FLOW
1.Start with your Composable UI, such as a button click or user input.
2.The UI event triggers a function in your ViewModel.
3.The ViewModel calls a Use Case (for example, “AddUserUseCase”).
4.The Use Case executes business logic and calls the Repository.
5.The Repository interacts with Room (for local data) or Retrofit (for API).
6.Once the data is returned, it flows back to the Use Case, then to the ViewModel.
7.The ViewModel updates its state (such as StateFlow).
8.The Composable observes that state and updates the UI with the new data.



# Notes App

## Features
- Add Notes
- Edit Notes
- Delete Notes
- Search Notes
- Categories
- Dark Mode

## Tech Stack
- Kotlin
- Compose
- Room
- Hilt
- MVVM

## Architecture
Clean Architecture + Repository Pattern
