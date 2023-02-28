package com.example.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentDashboardBinding
import com.example.notesapp.databinding.TodoItemBinding
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.entity.Note
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.view.adapters.NoteAdpater
import com.example.notesapp.view.adapters.RVAdapter
import com.example.notesapp.viewmodel.TodoViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var noteList: ArrayList<Note>
    private val todoVM by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
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
            todoBinding.todoItem.setText(it.title)
            todoBinding.noteCheckBox.apply{
                isChecked = it.isDone
                setOnClickListener { _-> todoVM.updateTodo( it.copy(isDone = isChecked) ) }
            }
            todoBinding.deletetodo.setOnClickListener {_ ->
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.deleting)
                    .setMessage(R.string.todoDeleteMsg)
                    .setNegativeButton(R.string.dialogNo) {_,_->}
                    .setPositiveButton(R.string.dialogYes) {_,_-> todoVM.deleteTodo(it) }
                    .show()
            }
        }
    }
    
    private lateinit var noteList: List<Note>

    var floatingBtnVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteInterface()
        initTodoList()
    }

    private fun noteInterface() {
/*
        noteList = arrayListOf(
            Note(
                "Programming Languages",
                "Java,Python,Kotlin,JavaScript",
                "2023 Feb 22",
                "",
                "14",
                "Black",
                "Gray",
                "true",
                "false",
                0, ""
            ),
            Note(
                "2023 New Year Resolution",
                "Lose 10 lbs, Wake up at 7 am, Eat healthy",
                "2023 Jan 1",
                "2222",
                "14",
                "Black",
                "Gray",
                "false",
                "true",
                0, ""
            ),
            Note(
                "Colorado Trip",
                "Spend three nights at Apsen, Get a airbnb with hottub, Rent a rental car, Go skiing",
                "2023 Feb 22",
                "",
                "14",
                "Black",
                "Gray",
                "true",
                "false",
                0, ""
            ),
            Note(
                "Programming Languages",
                "Java,Python,Kotlin,JavaScript",
                "2023 Feb 22",
                "",
                "14",
                "Black",
                "Gray",
                "true",
                "false",
                0, ""
            ),
            Note(
                "2023 New Year Resolution",
                "Lose 10 lbs, Wake up at 7 am, Eat healthy",
                "2023 Jan 1",
                "2222",
                "14",
                "Black",
                "Gray",
                "false",
                "true",
                0, ""
            ),
            Note(
                "Colorado Trip",
                "Spend three nights at Apsen, Get a airbnb with hottub, Rent a rental car, Go skiing",
                "2023 Feb 22",
                "",
                "14",
                "Black",
                "Gray",
                "true",
                "false",
                0, ""
            )

        )
*/
        binding.RVNotes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        noteList = AppDatabase.getInstance(requireContext()).getNoteDao().getAllNotes()
        binding.RVNotes.adapter = NoteAdpater(noteList)
    }

    private fun initTodoList() {
        binding.RVTodo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
        todoVM.allTodos.observe(viewLifecycleOwner) {
            it.isAdding?.let {isAdding ->
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