package com.example.notesapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentDashboardBinding
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.databinding.FragmentTodoBinding
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.view.adapters.TodoAdapter

class TodoFragment : Fragment() {
    private lateinit var binding: FragmentTodoBinding
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{
            newTodo.setOnKeyListener { view, keyCode, event ->
                return@setOnKeyListener keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
            }
        }
    }
}
