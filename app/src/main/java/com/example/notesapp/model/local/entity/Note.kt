package com.example.notesapp.model.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Note")
data class Note(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "passcode") val passcode: String,
    @ColumnInfo(name = "bodyFontSize") val bodyFontSize: String = "14",
    @ColumnInfo(name = "textColor") val textColor: String,
    @ColumnInfo(name = "isStarred") val isStarred: String,
    @ColumnInfo(name = "isLocked") val isLocked: String,
    @PrimaryKey(autoGenerate = true) var index: Int = 0,
    @ColumnInfo(name = "urlLink") var urlLink:String
) : Parcelable