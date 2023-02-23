package com.example.notesapp.view.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.FragmentTodoBinding
import com.example.notesapp.databinding.TodoItemBinding
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.presenter.TodoPresenter
import com.example.notesapp.view.adapters.RVAdapter

class TodoFragment : Fragment() {
    private lateinit var binding: FragmentTodoBinding
    private val presenter = TodoPresenter(this)

    private val todoItems = mutableListOf<Todo>()
    private lateinit var todoBinding: TodoItemBinding
    private val todoAdapter by lazy {
        RVAdapter(
            todoItems,
            {inflater, container, attach, vh ->
                TodoItemBinding.inflate(inflater, container, attach).apply {
                    todoBinding = this
                }.run { vh(root) }
            }
        ) {
            todoBinding.todoItem.setText(it.title)
            todoBinding.noteCheckBox.isChecked = it.isDone
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            newTodo.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    if("${newTodo.text}".isNotEmpty()) {
                        with ( Todo(
                            "${newTodo.text}",
                            0,
                            noteCheckBox.isChecked,
                            0 //need to add priority view
                        )) {
                            todoItems.add(this)
                            addedNotes.adapter?.notifyItemInserted(todoItems.size - 1)
                            presenter.addTodo(this)
                        }
                        newTodo.setText("")
                        noteCheckBox.isChecked = false
                    }
                    return@setOnKeyListener true
                } else {
                    return@setOnKeyListener false
                }
            }
        }

        binding.addedNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }
}
