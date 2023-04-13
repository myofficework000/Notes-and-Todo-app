package com.example.notesapp.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.dao.NoteDao
import com.example.notesapp.model.local.dao.TodoDao
import com.example.notesapp.model.local.entity.Todo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TodoDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var todoDao: TodoDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        todoDao = appDatabase.getTodoDao()
    }

    @Test
    fun testAddTodo() = runTest{
        val todo = Todo(
            0,
            "Todo 1",
            "Medium",
            "",
            false,
            "This is todo 1"
        )
        assertEquals(1,todoDao.addTodo(todo))
    }

    @Test
    fun testDeleteTodo() = runTest{
        val todo = Todo(
            1,
            "Todo 1",
            "Medium",
            "",
            false,
            "This is todo 1"
        )
        todoDao.addTodo(todo)
        assertEquals(1,todoDao.getAllTodo().size)

        assertEquals(1,todoDao.getAllTodo().last().index)
        todoDao.deleteTodo(todo)
        assertEquals(0,todoDao.getAllTodo().size)
    }
}