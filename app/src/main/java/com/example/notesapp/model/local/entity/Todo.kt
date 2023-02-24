package com.example.notesapp.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.model.Constants.TABLE_NAME_TODO

@Entity(tableName = TABLE_NAME_TODO)
data class Todo(
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true) var index: Int = 0,
    @ColumnInfo(name = "isDone") val isDone: Boolean,
    @ColumnInfo(name = "priority") var priority: String = "Medium"
)
