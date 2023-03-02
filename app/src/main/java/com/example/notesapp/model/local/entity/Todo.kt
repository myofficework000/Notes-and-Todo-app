package com.example.notesapp.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.model.Constants.TABLE_NAME_TODO

@Entity(tableName = TABLE_NAME_TODO)
data class Todo(
    @PrimaryKey(autoGenerate = true) val index: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "priority") val priority: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "isDone") val isDone: Boolean = false,
    @ColumnInfo(name = "description") val description: String = ""
)
