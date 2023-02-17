package com.example.notesapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.local.dao.NoteDao
import com.example.notesapp.model.local.entity.Note
import com.example.notesapp.model.local.entity.Todo

@Database(entities = [Note::class, Todo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBlogDao(): NoteDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "noteDB"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}