package com.example.roomdbkotlin.di

import android.content.Context
import androidx.room.Room
import com.example.roomdbkotlin.data.local.AppDatabase
import com.example.roomdbkotlin.data.local.UserDao
import com.example.roomdbkotlin.data.remote.ApiService
import com.example.roomdbkotlin.data.repository.UserRepositoryImpl
import com.example.roomdbkotlin.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "user_db"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(
                "https://jsonplaceholder.typicode.com/"
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

/*    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService {

        return retrofit.create(ApiService::class.java)
    }*/

    @Provides
    @Singleton
    fun provideRepository(
        dao: UserDao,
        api: ApiService,
        @ApplicationContext context: Context
    ): UserRepository {

        return UserRepositoryImpl(
            dao,
            api,
            context
        )
    }
}