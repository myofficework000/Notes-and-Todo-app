package com.example.notesapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.model.local.entity.Note
import java.util.Calendar

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var note: Note
    private lateinit var editingNote: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        initData()
        initViews()
        return binding.root
    }

    private fun initData() {
        initWithData() //in case of "Add note"
//        initWithData(Note(
//            "Title - in case of title",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            0,
//            ""
//        ))
    }

    private fun initWithData(data: Note? = null) {
        if(data != null) {
            note = data
        }
        else {
            note = Note(
                "",
                "",
                Calendar.getInstance().time.toString(),
                "OK",
                "14",
                "red",
                "false",
                "false",
                "",
                0,
                ""
            )
        }
        editingNote = note
    }

    private fun initViews() {
        binding.apply {
            inputTitle.setText(note.title)
            inputTitle.doAfterTextChanged {
                editingNote.title = inputTitle.text.toString()
            }
            inputBody.setText(note.body)
            inputBody.doAfterTextChanged {
                editingNote.body = inputBody.text.toString()
            }
            btnDelete.setOnClickListener {
                deleteNoteClick()
            }
            btnSave.setOnClickListener {
                saveNoteClick()
            }

            val iStarred:Boolean = editingNote.isStarred.toBoolean()
            if(iStarred) {
                btnStarred.setImageResource(R.drawable.baseline_star_24)
            } else {
                btnStarred.setImageResource(R.drawable.baseline_star_border_24)
            }
            btnStarred.setOnClickListener {
                var starred:Boolean = editingNote.isStarred.toBoolean()
                starred = !starred
                editingNote.isStarred = starred.toString()
                if(starred) {
                    btnStarred.setImageResource(R.drawable.baseline_star_24)
                } else {
                    btnStarred.setImageResource(R.drawable.baseline_star_border_24)
                }
            }

            val iLocked:Boolean = editingNote.isLocked.toBoolean()
            if(iLocked) {
                btnLocked.setImageResource(R.drawable.baseline_lock_24)
            } else {
                btnLocked.setImageResource(R.drawable.baseline_lock_open_24)
            }
            btnLocked.setOnClickListener {
                var locked:Boolean = editingNote.isLocked.toBoolean()
                locked = !locked
                editingNote.isLocked = locked.toString()
                if(locked) {
                    btnLocked.setImageResource(R.drawable.baseline_lock_24)
                } else {
                    btnLocked.setImageResource(R.drawable.baseline_lock_open_24)
                }
            }
        }
    }

    private fun deleteNoteClick() {
        if (note.title.isEmpty()) {
            Toast.makeText(requireContext(), "Nothing to delete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_SHORT).show()
            initData()
            initViews()
        }
    }

    private fun saveNoteClick() {
        if (editingNote != note) {
            note = editingNote
            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Nothing to save", Toast.LENGTH_SHORT).show()
        }
    }
}