package com.example.notesapp.model.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.model.Constants.TABLE_NAME_NOTES
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME_NOTES)
data class Note(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "body") var body: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "passcode") val passcode: String,
    @ColumnInfo(name = "bodyFontSize") var bodyFontSize: String = "14",
    @ColumnInfo(name = "textColor") var textColor: String,
    @ColumnInfo(name = "backgroundColor") var bgColor: String,
    @ColumnInfo(name = "isStarred") var isStarred: String,
    @ColumnInfo(name = "isLocked") var isLocked: String,
    @PrimaryKey(autoGenerate = true) var index: Int = 0,
    @ColumnInfo(name = "urlLink") var urlLink: String
) : Parcelable