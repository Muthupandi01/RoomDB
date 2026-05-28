package com.example.roomdbkotlin.di

import android.content.Context
import androidx.room.Room
import com.example.roomdbkotlin.data.local.AppDatabase
import com.example.roomdbkotlin.data.local.NoteDao
import com.example.roomdbkotlin.data.repository.NoteRepositoryImpl
import com.example.roomdbkotlin.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
            "notes_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(
        db: AppDatabase
    ): NoteDao {

        return db.noteDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        dao: NoteDao
    ): NoteRepository {

        return NoteRepositoryImpl(dao)
    }
}