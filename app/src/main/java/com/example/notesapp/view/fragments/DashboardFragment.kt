package com.example.notesapp.view.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentDashboardBinding
import com.example.notesapp.databinding.TodoItemBinding
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.view.activity.MainActivity
import com.example.notesapp.view.adapters.NoteAdapter
import com.example.notesapp.view.adapters.RVAdapter
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.TodoViewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var layoutManager: LinearLayoutManager
    private var floatingBtnVisible = false

    private val todoVM by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private val notesVM by lazy { ViewModelProvider(requireActivity())[NotesViewModel::class.java] }
    private val todoItems = mutableListOf<Todo>()
    private val todoAdapter by lazy {
        RVAdapter(
            todoItems,
            { inflater, container, attach, vh ->
                vh(TodoItemBinding.inflate(inflater, container, attach))
            }
        ) { data, b, _ ->
            var it = data
            val binding = b as TodoItemBinding
            binding.todoItem.apply {
                text = it.title
                strikeText(this, it.isDone)
            }

            binding.noteCheckBox.apply {
                isChecked = it.isDone
                setOnCheckedChangeListener { _, checked ->
                    todoVM.updateTodo(it.copy(isDone = checked))
                    it = it.copy(isDone = checked)
                    strikeText(binding.todoItem, checked)
                }
            }
            binding.deletetodo.setOnClickListener { _ ->
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.deleting)
                    .setMessage(R.string.todoDeleteMsg)
                    .setNegativeButton(R.string.dialogNo) { _, _ -> }
                    .setPositiveButton(R.string.dialogYes) { _, _ -> todoVM.deleteTodo(it) }
                    .show()
            }

            binding.root.setOnClickListener { _ ->
                TodoDetailFragment(it) { checked ->
                    todoVM.updateTodo(it.copy(isDone = checked))
                    strikeText(binding.todoItem, checked)
                    binding.noteCheckBox.isChecked = checked
                }.show(parentFragmentManager, "")
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
        notesVM.getAlNotes()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.RVNotes.layoutManager = layoutManager
        observeViewChanges()
        initTodoList()
        initFloatingButton()
    }

    override fun onResume() {
        super.onResume()
        notesVM.getAlNotes()
    }
    private fun initFloatingButton() {
        binding.apply {
            noteTodoBtn.setOnClickListener {
                if (!floatingBtnVisible) {
                    expandFloatingButton()
                } else {
                    hideFloatingButton()
                }
            }
            todoBtn.setOnClickListener {
                hideFloatingButton()
                findNavController().navigate(R.id.todoDialog)

            }
            noteBtn.setOnClickListener {
                hideFloatingButton()
                findNavController().navigate(R.id.notesFragment3)
            }
        }
    }
    private fun expandFloatingButton(){
        binding.apply{
            noteBtn.show()
            todoBtn.show()
            noteBtn.visibility = View.VISIBLE
            todoBtn.visibility = View.VISIBLE
            noteTodoBtn.setImageResource(R.drawable.baseline_floating_close_24)
            floatingBtnVisible = true
        }
    }

    private fun hideFloatingButton() {
        binding.apply {
            noteBtn.hide()
            todoBtn.hide()
            noteBtn.visibility = View.GONE
            todoBtn.visibility = View.GONE
            noteTodoBtn.setImageResource(R.drawable.baseline_add_24)
            floatingBtnVisible = false
        }
    }

    private fun observeViewChanges() {
        notesVM.allNotes.observe(this.viewLifecycleOwner){
            it?.let {
                binding.RVNotes.adapter = NoteAdapter(it,requireContext())
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
    private fun strikeText(textView: TextView, isStruck: Boolean) = textView.apply {
        paintFlags = if (isStruck)
            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else
            paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}