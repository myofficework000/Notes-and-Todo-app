package com.example.notesapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.FragmentTodoBinding
import com.example.notesapp.databinding.TodoItemBinding
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.view.adapters.RVAdapter
import com.example.notesapp.view.adapters.TodoAdapter

class TodoFragment : Fragment() {
    private lateinit var binding: FragmentTodoBinding
    private lateinit var todoAdapter: TodoAdapter

    private val todoItems = mutableListOf<Todo>()
    private lateinit var todoBinding: TodoItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoBinding.inflate(inflater,container,false)
        todoItems.addAll(placeholderTodo)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{
            newTodo.setOnKeyListener { _, keyCode, event ->
                return@setOnKeyListener keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
            }
        }

        binding.addedNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RVAdapter(
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
    }

    companion object {
        val placeholderTodo = listOf(
            Todo("The power of water", isDone = false),
            Todo("is its ability", isDone = true),
            Todo("to take", isDone = false),
            Todo("any shape", isDone = true)
        )
    }
}
