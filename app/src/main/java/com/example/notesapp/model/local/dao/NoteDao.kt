package com.example.notesapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.model.Constants.TABLE_NAME_NOTES
import com.example.notesapp.model.local.entity.Note

@Dao
interface NoteDao {
    @Insert
    //fun insert(note: Note)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM $TABLE_NAME_NOTES")
    suspend fun getAllNotes(): List<Note>
}