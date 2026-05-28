package com.example.roomdbkotlin.domain.usecase

import com.example.roomdbkotlin.data.local.NoteEntity
import com.example.roomdbkotlin.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    operator fun invoke() =
        repository.getNotes()
}

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    operator fun invoke(
        query: String
    ) = repository.searchNotes(query)
}

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(
        note: NoteEntity
    ) {

        repository.insert(note)
    }
}

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(
        note: NoteEntity
    ) {

        repository.delete(note)
    }
}

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(
        note: NoteEntity
    ) {

        repository.update(note)
    }
}