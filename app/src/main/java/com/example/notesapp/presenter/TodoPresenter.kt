package com.example.notesapp.presenter

import android.view.View
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.view.fragments.TodoFragment

class TodoPresenter(
    private val view: TodoFragment
) {
    // It needs to be assured that dao exists at this stage. Otherwise it's worth crashing.
    private val dao by lazy { AppDatabase.getInstance(view.requireContext())!!.getTodoDao() }

    fun getAllTodo() = dao.getAllTodo()
    fun addTodo(data: Todo) = dao.addTodo(data)
    fun updateTodo(data: Todo) = dao.updateTodo(data)
    fun deleteTodo(data: Todo) = dao.deleteTodo(data)
}