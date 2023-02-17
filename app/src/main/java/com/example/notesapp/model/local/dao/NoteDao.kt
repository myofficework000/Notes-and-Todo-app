package com.example.notesapp.model.local.dao

import androidx.room.*
import com.example.notesapp.model.local.entity.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes(): MutableList<Note>
}