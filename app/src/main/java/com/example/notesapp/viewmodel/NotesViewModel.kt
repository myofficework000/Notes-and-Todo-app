package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes : LiveData<List<Note>> = _allNotes
    private val dao by lazy { AppDatabase.getInstance(application).getNoteDao() }

    fun getAlNotes() {
        viewModelScope.launch {
            _allNotes.value = dao.getAllNotes()
        }
    }

    fun insert(note: Note){
        viewModelScope.launch {
            dao.insert(note)
            getAlNotes()
        }
    }

    fun update(note: Note){
        viewModelScope.launch {
            dao.update(note)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            dao.delete(note)
        }
    }
}