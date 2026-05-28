package com.example.roomdbkotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}