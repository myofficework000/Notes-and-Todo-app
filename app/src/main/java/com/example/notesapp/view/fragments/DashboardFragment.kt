package com.example.notesapp.view.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentDashboardBinding
import com.example.notesapp.databinding.TodoItemBinding
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.view.adapters.NoteAdapter
import com.example.notesapp.view.adapters.RVAdapter
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.TodoViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val todoVM by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val notesVM by lazy { ViewModelProvider(requireActivity())[NotesViewModel::class.java] }
    private val todoItems = mutableListOf<Todo>()
    private lateinit var todoBinding: TodoItemBinding
    private val todoAdapter by lazy {
        RVAdapter(
            todoItems,
            { inflater, container, attach, vh ->
                TodoItemBinding.inflate(inflater, container, attach).apply {
                    todoBinding = this
                }.run { vh(root) }
            }
        ) { it, _ ->
            todoBinding.todoItem.text = it.title

            todoBinding.noteCheckBox.apply {
                isChecked = it.isDone
                setOnClickListener { _ ->
                    todoVM.updateTodo(it.copy(isDone = isChecked))
                    todoBinding.todoItem.apply {
                        if(isChecked){
                            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        }else{
                            paintFlags = 0
                        }
                    }

                }
            }
            todoBinding.deletetodo.setOnClickListener { _ ->
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.deleting)
                    .setMessage(R.string.todoDeleteMsg)
                    .setNegativeButton(R.string.dialogNo) { _, _ -> }
                    .setPositiveButton(R.string.dialogYes) { _, _ -> todoVM.deleteTodo(it) }
                    .show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteInterface()
        initTodoList()
    }

    private fun noteInterface() {
        binding.RVNotes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        notesVM.allNotes.observe(this.viewLifecycleOwner) {

            it?.let {
                Toast.makeText(requireContext(), it.size.toString(),Toast.LENGTH_SHORT).show()
                binding.RVNotes.adapter = NoteAdapter(it)
            }
        }
    }

    private fun initTodoList() {
        binding.RVTodo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
        todoVM.allTodos.observe(this.viewLifecycleOwner) {
            it.isAdding?.let { isAdding ->
                if (isAdding) {
                    if (it.newList.isEmpty()) return@let
                    if (it.updateRangeIndex.first == it.updateRangeIndex.second) {
                        todoItems.add(it.newList[it.updateRangeIndex.first])
                        todoAdapter.notifyItemInserted(it.updateRangeIndex.first)
                    } else {
                        todoItems.addAll(
                            it.newList.subList(
                                it.updateRangeIndex.first, it.updateRangeIndex.second
                            )
                        )
                        todoAdapter.notifyItemRangeInserted(
                            it.updateRangeIndex.first, it.updateRangeIndex.second
                        )
                    }
                } else {
                    if (it.updateRangeIndex.first == it.updateRangeIndex.second) {
                        todoItems.removeAt(it.updateRangeIndex.first)
                        todoAdapter.notifyItemRemoved(it.updateRangeIndex.first)
                    }
                }
            }
        }
    }
}