package com.example.roomdbkotlin.domain.repository


import com.example.roomdbkotlin.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<NoteEntity>>

    fun searchNotes(
        query: String
    ): Flow<List<NoteEntity>>

    suspend fun insert(note: NoteEntity)

    suspend fun delete(note: NoteEntity)

    suspend fun update(note: NoteEntity)

    suspend fun updateFavorite(
        id: Int,
        isFavorite: Boolean
    )
}