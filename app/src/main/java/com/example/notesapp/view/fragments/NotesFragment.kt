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
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.dao.NoteDao
import com.example.notesapp.model.local.entity.Note
import java.util.Calendar

fun Note.copy(): Note = Note(
    this.title,
    this.body,
    this.date,
    this.passcode,
    this.bodyFontSize,
    this.textColor,
    this.bgColor,
    this.isStarred,
    this.isLocked,
    this.index,
    this.urlLink,
)

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var note: Note
    private lateinit var editingNote: Note
    private lateinit var db: AppDatabase
    private lateinit var noteDao: NoteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        initDatabase()
        val noteParam = arguments?.getParcelable<Note>("note")
        initData(noteParam)
        initViews()
        return binding.root
    }

    private fun initDatabase() {
        db = AppDatabase.getInstance(requireContext())
        noteDao = db.getNoteDao()
    }

    private fun createEmptyEditingNote() : Note {
        return Note(
            "",
            "",
            Calendar.getInstance().time.toString(),
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            ""
        )
    }

    private fun initData(noteParam: Note?) {
        if(noteParam != null) {
            note = noteParam
            editingNote = noteParam.copy()
        }
        else {
            note = createEmptyEditingNote()
            editingNote = createEmptyEditingNote()
        }
    }
    private fun initViews() {
        binding.apply {
            btnClose.setOnClickListener {
                activity?.supportFragmentManager?.popBackStackImmediate()
            }
            inputTitle.setText(editingNote.title)
            inputTitle.doAfterTextChanged {
                editingNote.title = inputTitle.text.toString()
            }
            inputBody.setText(editingNote.body)
            inputBody.doAfterTextChanged {
                editingNote.body = inputBody.text.toString()
            }
            inputFontSize.setText(editingNote.bodyFontSize)
            inputFontSize.doAfterTextChanged {
                editingNote.bodyFontSize = inputFontSize.text.toString()
            }
            inputTextColor.setText(editingNote.textColor)
            inputTextColor.doAfterTextChanged {
                editingNote.textColor = inputTextColor.text.toString()
            }
            inputBackgroundColor.setText(editingNote.bgColor)
            inputBackgroundColor.doAfterTextChanged {
                editingNote.bgColor = inputBackgroundColor.text.toString()
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
        if (note.index == 0) {
            Toast.makeText(requireContext(), "Nothing to delete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_SHORT).show()
            noteDao.delete(note)
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun saveNoteClick() {
        if (editingNote != note) {
            if(note.index <= 0) {
                note = editingNote.copy()
                noteDao.insert(note)
            }
            else {
                note = editingNote.copy()
                noteDao.update(note)
            }
            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Nothing to save", Toast.LENGTH_SHORT).show()
        }
    }

}