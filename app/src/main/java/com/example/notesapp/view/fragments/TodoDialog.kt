package com.example.notesapp.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.R
import com.example.notesapp.databinding.TodoDialogBinding
import com.example.notesapp.model.local.entity.Todo
import com.example.notesapp.viewmodel.TodoViewModel
import java.util.*

class TodoDialog : DialogFragment() {
    private val todoVM by lazy { ViewModelProvider(this)[TodoViewModel::class.java] }
    private lateinit var binding: TodoDialogBinding
    private val todoItems = mutableListOf<Todo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TodoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addItemsToSpinner()
        val isDoneDefault = false
        binding.apply {
            doneIcon.setOnClickListener {
                if ("${todoItemEdit.text}".isNotEmpty()) {
                    with(
                        Todo(
                            title = todoItemEdit.text.toString(),
                            priority = prioritySpinner.editText?.text.toString(),
                            category = categorySpinner.editText?.text.toString(),
                            isDone = isDoneDefault
                        )
                    ) {
                        todoItems.add(this)
                        todoVM.addTodo(this)
                        notifySuccess()
                        clearForm()
                    }
                    todoItemEdit.setText("")
                }
            }
            cancelIcon.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    private fun addItemsToSpinner() {
        val defaultPriorityList = resources.getStringArray(R.array.Priority)
        (binding.prioritySpinner.editText as AutoCompleteTextView).apply {
            setAdapter(ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                defaultPriorityList
            ))
            setOnFocusChangeListener { _, b ->
                if (b) showDropDown()
                else if (!defaultPriorityList.contains(text.toString()) &&
                    (!text.isDigitsOnly() || text.isBlank())
                ) setText(getString(R.string.priorityDefault))
            }
            addTextChangedListener {
                if (it?.isBlank() != false)
                    Handler(Looper.getMainLooper())
                        .postDelayed({ showDropDown() }, 100)
            }
        }

        (binding.categorySpinner.editText as AutoCompleteTextView).apply {
            setAdapter(ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.Category)
            ))
            setOnFocusChangeListener { _, b -> if (b) showDropDown() }
            addTextChangedListener {
                if (it?.isBlank() != false)
                    Handler(Looper.getMainLooper())
                        .postDelayed({ showDropDown() }, 100)
            }
        }
    }

    private fun clearForm() {
        binding.todoItemEdit.text?.clear()
        binding.prioritySpinner.editText?.setText(getString(R.string.priorityDefault))
        binding.categorySpinner.editText?.text?.clear()
    }

    private fun notifySuccess() {
        val builder = AlertDialog.Builder(requireContext()).apply{
            setMessage("Item added")
            setIcon(R.drawable.baseline_notifications_24)
            .setCancelable(true)
        }
        val dialog = builder.create()
        dialog.setTitle("Notification")
        dialog.show()

        val timer = Timer()
        timer.schedule(object: TimerTask(){
            override fun run(){
                dialog.dismiss()
                timer.cancel()
            }
        },700)
    }
    override fun onResume() {
        super.onResume()
        customizeDialog()
    }
    private fun customizeDialog() {
        val params = dialog?.window?.attributes
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.apply{
            setBackgroundDrawableResource(R.drawable.round_corner)
            attributes = params
        }
    }
    override fun onStop() {
        super.onStop()
        customizeDialog()
    }
}
