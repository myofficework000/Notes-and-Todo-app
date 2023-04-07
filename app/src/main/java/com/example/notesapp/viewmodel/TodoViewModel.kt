package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.entity.Todo
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val _allTodos = MutableLiveData<ListWithUpdate<Todo>>(listWithUpdateOf())
    val allTodos: LiveData<ListWithUpdate<Todo>> get() = _allTodos
    private val dao by lazy { AppDatabase.getInstance(application).getTodoDao() }

    init {
        viewModelScope.launch{
            // It needs to be assured that dao exists at this stage. Otherwise it's worth crashing.
            with(dao.getAllTodo()) {
                _allTodos.value = listWithUpdateOf(this, Pair(0, this.size), true)
            }
        }
    }

    fun addTodo(data: Todo) = viewModelScope.launch {
        with(data) {
            dao.addTodo(this).also {
                _allTodos.setValue(_allTodos.value?.add(this.copy(index = it)))
            }
        }
    }


    fun updateTodo(data: Todo) = viewModelScope.launch{
            dao.updateTodo(data)
        }
    fun deleteTodo(data: Todo) = viewModelScope.launch {
        dao.deleteTodo(data).also {
            _allTodos.value?.apply {
                _allTodos.setValue(this.remove(data))
            }
        }
    }


    data class ListWithUpdate<T>(
        val newList: List<T>,
        val updateRangeIndex: Pair<Int, Int>,
        val isAdding: Boolean?
    ) {
        fun add(item: T) = ListWithUpdate(
            newList.plus(item),
            Pair(newList.size, newList.size),
            true
        )

        fun addAll(items: List<T>) = ListWithUpdate(
            newList.plus(items),
            Pair(newList.size, newList.size + items.size),
            true
        )

        fun remove(item: T) = with(newList.indexOf(item)) {
            if (this == -1) return ListWithUpdate(newList, Pair(-1, -1), null)
            ListWithUpdate(newList.minus(item), Pair(this, this), false)
            // Using minus here expects the list to not have duplicate items.
        }
    }

    fun <T> listWithUpdateOf(
        initItems: List<T> = listOf(),
        updateRange: Pair<Int, Int> = Pair(-1, -1),
        isAdding: Boolean? = null // true: Add, false: Remove, null: Others
    ) = ListWithUpdate(initItems, updateRange, isAdding)
}