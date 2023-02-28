package com.example.notesapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.model.Constants.TABLE_NAME_TODO
import com.example.notesapp.model.local.entity.Todo

@Dao
interface TodoDao {
    @Query("select * from $TABLE_NAME_TODO")
    fun getAllTodo(): List<Todo>
    @Insert
    fun addTodo(data: Todo)
    @Update
    fun updateTodo(data: Todo)
    @Delete
    fun deleteTodo(data: Todo)
}