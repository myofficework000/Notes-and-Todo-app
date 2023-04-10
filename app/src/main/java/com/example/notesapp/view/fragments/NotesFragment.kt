package com.example.notesapp.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.ColorDialogBinding
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.databinding.FsDialogBinding
import com.example.notesapp.databinding.LinkDialogBinding
import com.example.notesapp.model.Constants
import com.example.notesapp.model.Constants.FONT_SIZES
import com.example.notesapp.model.Constants.SOFT_COLORS
import com.example.notesapp.model.Constants.STRONG_COLORS
import com.example.notesapp.model.local.AppDatabase
import com.example.notesapp.model.local.dao.NoteDao
import com.example.notesapp.model.local.entity.Note
import com.example.notesapp.utils.Common.stringInt
import com.example.notesapp.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import java.util.*


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
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        initViewModel()
        initDatabase()
        val noteParam = arguments?.getParcelable<Note>("note")
        initData(noteParam)
        initViews()
        return binding.root
    }

    private fun initViewModel() {
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
    }

    private fun initDatabase() {
        db = AppDatabase.getInstance(requireContext())
        noteDao = db.getNoteDao()
    }

    private fun createEmptyEditingNote(): Note {
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
        if (noteParam != null) {
            note = noteParam
            editingNote = noteParam.copy()
        } else {
            note = createEmptyEditingNote()
            editingNote = createEmptyEditingNote()
        }
    }

    private fun updateUIBackgroundColor(colorNo: Int) {
        binding.layoutToolbar.setBackgroundColor(Color.parseColor(STRONG_COLORS[colorNo]))
        binding.notesFragment.setBackgroundColor(Color.parseColor(SOFT_COLORS[colorNo]))
    }

    private fun updateUITextColor(colorNo: Int) {
        binding.inputTitle.setTextColor(Color.parseColor(STRONG_COLORS[colorNo]))
        binding.inputBody.setTextColor(Color.parseColor(STRONG_COLORS[colorNo]))
    }

    private fun updateUIFontSize(fsNo: Int) {
        var fontSize = fsNo
        if (fontSize > FONT_SIZES.size - 1) fontSize = FONT_SIZES.size - 1
        binding.inputTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, FONT_SIZES[fontSize].toFloat())
        binding.inputBody.setTextSize(TypedValue.COMPLEX_UNIT_PX, FONT_SIZES[fontSize].toFloat())
    }

    private fun updateLinkText(urlLinkText: String) {
        val underlineLink = SpannableString(urlLinkText)
        underlineLink.setSpan(UnderlineSpan(), 0, underlineLink.length, 0)
        binding.txtLink.text = underlineLink
        binding.txtLink.setOnClickListener {
            openBrowser(urlLinkText)
        }
    }

    private fun openBrowser(urlLinkText: String) {
        if (urlLinkText.contains("https://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlLinkText))
            requireContext().startActivity(intent)
        } else {
            Toast.makeText(requireContext(), getString(R.string.bad_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        binding.apply {

            updateLinkText(editingNote.urlLink)
            updateUIBackgroundColor(stringInt(editingNote.bgColor, true))
            updateUITextColor(stringInt(editingNote.textColor, true))
            updateUIFontSize(stringInt(editingNote.bodyFontSize))
            btnFontSize.setOnClickListener {
                val fsDialogBinding: FsDialogBinding = FsDialogBinding.inflate(layoutInflater)

                val builder = AlertDialog.Builder(requireContext()).apply {
                    setView(fsDialogBinding.root)
                    setCancelable(false)
                }
                fsDialogBinding.spinnerFontSize.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, FONT_SIZES)
                fsDialogBinding.spinnerFontSize.setSelection(stringInt(editingNote.bodyFontSize))
                fsDialogBinding.spinnerFontSize.onItemSelectedListener =
                    object : OnItemSelectedListener {
                        override fun onItemSelected(
                            parentView: AdapterView<*>?,
                            selectedItemView: View,
                            position: Int,
                            id: Long
                        ) {
                            editingNote.bodyFontSize = position.toString()
                            updateUIFontSize(stringInt(editingNote.bodyFontSize))
                        }

                        override fun onNothingSelected(parentView: AdapterView<*>?) {
                        }
                    }
                val dialog = builder.create()
                dialog.window?.setGravity(Gravity.CENTER)
                fsDialogBinding.apply {
                    btnComplete.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }
            btnBackgroundColor.setOnClickListener {
                val colorDlgBinding: ColorDialogBinding = ColorDialogBinding.inflate(layoutInflater)
                var btnColorList = mutableListOf(
                    colorDlgBinding.btnColor00,
                    colorDlgBinding.btnColor01,
                    colorDlgBinding.btnColor02,
                    colorDlgBinding.btnColor03,
                    colorDlgBinding.btnColor04,
                    colorDlgBinding.btnColor05,
                    colorDlgBinding.btnColor06,
                    colorDlgBinding.btnColor07,
                    colorDlgBinding.btnColor08,
                )
                btnColorList.forEachIndexed { idx, btn ->
                    btn.setBackgroundColor(Color.parseColor(STRONG_COLORS[idx]))
                    btn.setOnClickListener {
                        editingNote.bgColor = idx.toString()
                        updateUIBackgroundColor(idx)
                    }
                }
                val builder = AlertDialog.Builder(requireContext()).apply {
                    setView(colorDlgBinding.root)
                    setCancelable(false)
                }
                val dialog = builder.create()
                dialog.window?.setGravity(Gravity.CENTER)
                colorDlgBinding.apply {
                    btnComplete.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }
            btnTextColor.setOnClickListener {
                val colorDlgBinding: ColorDialogBinding = ColorDialogBinding.inflate(layoutInflater)
                var btnColorList = mutableListOf(
                    colorDlgBinding.btnColor00,
                    colorDlgBinding.btnColor01,
                    colorDlgBinding.btnColor02,
                    colorDlgBinding.btnColor03,
                    colorDlgBinding.btnColor04,
                    colorDlgBinding.btnColor05,
                    colorDlgBinding.btnColor06,
                    colorDlgBinding.btnColor07,
                    colorDlgBinding.btnColor08,
                )
                btnColorList.forEachIndexed { idx, btn ->
                    btn.setBackgroundColor(Color.parseColor(STRONG_COLORS[idx]))
                    btn.setOnClickListener {
                        editingNote.textColor = idx.toString()
                        updateUITextColor(idx)
                    }
                }
                val builder = AlertDialog.Builder(requireContext()).apply {
                    setView(colorDlgBinding.root)
                    setCancelable(false)
                }
                val dialog = builder.create()
                dialog.window?.setGravity(Gravity.CENTER)
                colorDlgBinding.apply {
                    btnComplete.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }
            btnLink.setOnClickListener {
                val linkDlgBinding: LinkDialogBinding = LinkDialogBinding.inflate(layoutInflater)
                linkDlgBinding.inputLink.editText?.setText(editingNote.urlLink)
                linkDlgBinding.inputLink.editText?.doAfterTextChanged {
                    editingNote.urlLink = it.toString()
                    updateLinkText(editingNote.urlLink)
                }
                val builder = AlertDialog.Builder(requireContext()).apply {
                    setView(linkDlgBinding.root)
                    setCancelable(false)
                }
                val dialog = builder.create()
                dialog.window?.setGravity(Gravity.CENTER)
                linkDlgBinding.apply {
                    btnComplete.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }
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
            btnDelete.setOnClickListener {
                deleteNoteClick()
            }
            btnSave.setOnClickListener {
                saveNoteClick()
            }

            val iStarred: Boolean = editingNote.isStarred.toBoolean()
            if (iStarred) {
                btnStarred.setImageResource(R.drawable.baseline_star_24)
            } else {
                btnStarred.setImageResource(R.drawable.baseline_star_border_24)
            }
            btnStarred.setOnClickListener {
                var starred: Boolean = editingNote.isStarred.toBoolean()
                starred = !starred
                editingNote.isStarred = starred.toString()
                if (starred) {
                    btnStarred.setImageResource(R.drawable.baseline_star_24)
                } else {
                    btnStarred.setImageResource(R.drawable.baseline_star_border_24)
                }
            }

            val iLocked: Boolean = editingNote.isLocked.toBoolean()
            if (iLocked) {
                btnLocked.setImageResource(R.drawable.baseline_lock_24)
            } else {
                btnLocked.setImageResource(R.drawable.baseline_lock_open_24)
            }
            btnLocked.setOnClickListener {
                var locked: Boolean = editingNote.isLocked.toBoolean()
                locked = !locked
                editingNote.isLocked = locked.toString()
                if (locked) {
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
            notesViewModel.delete(note)
            findNavController().navigate(R.id.action_notesFragment3_to_dashboardFragment3)
        }
    }

    private fun saveNoteClick() {
        if (editingNote != note) {
            if (note.index <= 0) {
                note = editingNote.copy()
                notesViewModel.insert(note)

            } else {
                note = editingNote.copy()
                notesViewModel.update(note)
            }
            Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_notesFragment3_to_dashboardFragment3)
        } else {
            Toast.makeText(requireContext(), "Nothing to save", Toast.LENGTH_SHORT).show()
        }
    }

}