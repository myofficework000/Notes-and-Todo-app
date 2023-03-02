package com.example.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentTodoDetailBinding
import com.example.notesapp.model.local.entity.Todo

class TodoDetailFragment(
    private val data: Todo,
    private val editTodoDone: (Boolean) -> Unit
) : DialogFragment() {
    private lateinit var binding: FragmentTodoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTodoDetailBinding.inflate(inflater, container, false).apply {
        binding = this
        todoDetailPriority.text = getString(R.string.detail_priority, data.priority)
        todoDetailCategory.text = data.category
        todoDetailTitle.text = data.title
        todoDetailDescription.text = data.description
        todoDetailIsDone.isChecked = data.isDone
        todoDetailIsDone.text = getString(
            if (data.isDone) R.string.mark_as_incomplete else R.string.mark_as_complete
        )
        todoDetailIsDone.setOnCheckedChangeListener {_, checked ->
            editTodoDone(checked)
            todoDetailIsDone.text = getString(
                if (checked) R.string.mark_as_incomplete else R.string.mark_as_complete
            )
        }
        todoDetailClose.setOnClickListener { dismiss() }
    }.root

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            attributes = attributes?.apply {
                width = WindowManager.LayoutParams.MATCH_PARENT
                horizontalMargin = resources.getDimension(R.dimen._16dp)
            }
        }
    }
}