package com.example.notesapp.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.dao.NoteDao
import com.example.notesapp.model.local.entity.Note
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        noteDao = appDatabase.getNoteDao()
    }

    @Test
    fun testInsertIntoDatabase() = runTest{
            val note = Note(
                "Note 1",
                "this is note 1",
                "04/13/2023",
                "12345",
                "",
                "White",
                "",
                "",
                "",
                0,
                ""
            )
            noteDao.insert(note)
            assertEquals(1,noteDao.getAllNotes().size)
    }

    @Test
    fun testUpdateNoteDatabase() = runTest{
        var insertNote = Note(
            "Note 1",
            "this is note 1",
            "04/13/2023",
            "12345",
            "",
            "White",
            "",
            "",
            "",
            0,
            ""
        )
        val insertedId = noteDao.insert(insertNote)
        insertNote.title = "Note 2"
        insertNote.index = insertedId.toInt()

        noteDao.update(insertNote)

        val noteList = noteDao.getAllNotes()
        assertEquals(1, noteList.size)
        assertEquals("Note 2", noteList[0].title)
    }

    @Test
    fun testDeleteItem() = runTest{
        val note = Note(
            "Note 1",
            "this is note 1",
            "04/13/2023",
            "12345",
            "",
            "White",
            "",
            "",
            "",
            1,
            ""
        )
        noteDao.insert(note)
        assertEquals(1,noteDao.getAllNotes().size)

        noteDao.delete(note)
        assertEquals(0,noteDao.getAllNotes().size)
    }

    @After
    fun closeDatabase(){
        appDatabase.close()
    }
}