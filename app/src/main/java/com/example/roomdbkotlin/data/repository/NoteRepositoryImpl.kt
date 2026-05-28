package com.example.roomdbkotlin.data.repository

import com.example.roomdbkotlin.data.local.NoteDao
import com.example.roomdbkotlin.data.local.NoteEntity
import com.example.roomdbkotlin.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes():
            Flow<List<NoteEntity>> {

        return dao.getNotes()
    }

    override fun searchNotes(
        query: String
    ): Flow<List<NoteEntity>> {

        return dao.searchNotes(query)
    }

    override suspend fun insert(
        note: NoteEntity
    ) {

        dao.insert(note)
    }

    override suspend fun delete(
        note: NoteEntity
    ) {

        dao.delete(note)
    }

    override suspend fun update(
        note: NoteEntity
    ) {

        dao.update(note)
    }

    override suspend fun updateFavorite(
        id: Int,
        isFavorite: Boolean
    ) {

        dao.updateFavorite(
            id,
            isFavorite
        )
    }
}