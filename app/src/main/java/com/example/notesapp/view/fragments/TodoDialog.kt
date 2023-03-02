package com.example.notesapp.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.notesapp.utils.Common
import com.example.notesapp.viewmodel.TodoViewModel
import java.util.*

class TodoDialog : DialogFragment() {
    private val todoVM by lazy { ViewModelProvider(requireActivity())[TodoViewModel::class.java] }
    private lateinit var binding: TodoDialogBinding
    private val todoItems = mutableListOf<Todo>()
    private val handler = Handler(Looper.getMainLooper())

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
            addBtn.setOnClickListener {
                if ("${todoItemEdit.text}".isNotEmpty()) {
                    with(
                        Todo(
                            title = todoItemEdit.text.toString(),
                            description = descriptionEdit.text.toString(),
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
                    dialog?.dismiss()
                } else {
                    binding.newTaskEditLayout.error = getString(R.string.todo_title_empty_error)
                }
            }
            cancelBtn.setOnClickListener {
                dialog?.dismiss()
            }
            todoItemEdit.addTextChangedListener {
                binding.newTaskEditLayout.error = null
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
                else {
                    dismissDropDown()
                    if (!defaultPriorityList.contains(text.toString()) &&
                        (!text.isDigitsOnly() || text.isBlank())
                    ) setText(getString(R.string.priorityDefault))
                }
            }
            setOnClickListener {  showDropDown() }
            addTextChangedListener {
                if (it?.isBlank() != false)
                    handler.postDelayed({ if (dialog != null) showDropDown() }, 100)
            }
        }

        (binding.categorySpinner.editText as AutoCompleteTextView).apply {
            setAdapter(ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.Category)
            ))
            setOnFocusChangeListener { _, b -> if (b) showDropDown() else dismissDropDown() }
            setOnClickListener { showDropDown() }
            addTextChangedListener {
                if (it?.isBlank() != false)
                    handler.postDelayed({ if (dialog != null) showDropDown() }, 100)
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
                handler.removeCallbacksAndMessages(null)
                dialog.dismiss()
                timer.cancel()
            }
        },900)
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
