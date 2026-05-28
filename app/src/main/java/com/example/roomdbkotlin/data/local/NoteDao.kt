package com.example.roomdbkotlin.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Query("""
        SELECT * FROM notes
        ORDER BY createdAt DESC
    """)
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM notes
        WHERE title LIKE '%' || :query || '%'
        OR content LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun searchNotes(
        query: String
    ): Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM notes
        WHERE category = :category
    """)
    fun getByCategory(
        category: String
    ): Flow<List<NoteEntity>>

    @Query("""
UPDATE notes
SET isFavorite = :isFavorite
WHERE id = :id
""")
    suspend fun updateFavorite(
        id: Int,
        isFavorite: Boolean
    )
}