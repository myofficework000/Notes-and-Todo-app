package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.entity.Todo

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    val allTodos: LiveData<List<Todo>>
    val dao by lazy { AppDatabase.getInstance(application).getTodoDao() }

    init {
        allTodos = dao.getAllTodo()
        // It needs to be assured that dao exists at this stage. Otherwise it's worth crashing.
    }

    fun addTodo(data: Todo) = dao.addTodo(data)
    fun updateTodo(data: Todo) = dao.updateTodo(data)
    fun deleteTodo(data: Todo) = dao.deleteTodo(data)
}