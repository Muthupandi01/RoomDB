package com.example.roomdbkotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdbkotlin.data.local.NoteEntity
import com.example.roomdbkotlin.data.utils.Resource
import com.example.roomdbkotlin.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notesState =
        MutableStateFlow<Resource<List<NoteEntity>>>(
            Resource.Loading()
        )

    val notesState =
        _notesState.asStateFlow()

    private val _searchQuery =
        MutableStateFlow("")

    init {
        getNotes()
    }

    fun onSearchChange(query: String) {

        _searchQuery.value = query

        searchNotes(query)
    }

    fun insert(
        title: String,
        content: String,
        category: String
    ) {

        if (
            title.isBlank() ||
            content.isBlank()
        ) return

        viewModelScope.launch {

            repository.insert(
                NoteEntity(
                    title = title,
                    content = content,
                    category = category
                )
            )
        }
    }

    fun delete(note: NoteEntity) {

        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun update(
        id: Int,
        title: String,
        content: String,
        category: String
    ) {

        viewModelScope.launch {

            repository.update(
                NoteEntity(
                    id = id,
                    title = title,
                    content = content,
                    category = category
                )
            )
        }
    }

    private fun getNotes() {

        viewModelScope.launch {

            repository.getNotes().collect {

                _notesState.value =
                    Resource.Success(it)
            }
        }
    }

    private fun searchNotes(query: String) {

        viewModelScope.launch {

            repository.searchNotes(query)
                .collect {

                    _notesState.value =
                        Resource.Success(it)
                }
        }
    }

    fun toggleFavorite(note: NoteEntity) {

        viewModelScope.launch {

            repository.updateFavorite(
                id = note.id,
                isFavorite = !note.isFavorite
            )
        }
    }
}