package com.example.notesapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.Constants
import com.example.notesapp.model.local.dao.NoteDao
import com.example.notesapp.model.local.dao.TodoDao
import com.example.notesapp.model.local.entity.Note
import com.example.notesapp.model.local.entity.Todo

@Database(entities = [Note::class, Todo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
    abstract fun getTodoDao(): TodoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.NOTE_DB
                ).build()
            }
            return INSTANCE as AppDatabase
        }
    }
}