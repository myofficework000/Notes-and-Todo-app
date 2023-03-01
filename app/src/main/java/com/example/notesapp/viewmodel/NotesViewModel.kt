package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.entity.Note

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    private val dao by lazy { AppDatabase.getInstance(application).getNoteDao() }

    init {
        allNotes = dao.getAllNotes()
    }
}