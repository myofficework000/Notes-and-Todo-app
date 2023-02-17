package com.example.notesapp.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class Todo(
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true) var index: Int = 0,
    @ColumnInfo(name = "isDone") val isDone: Boolean,
    @ColumnInfo(name = "priority") var priority: Int = 0
)
