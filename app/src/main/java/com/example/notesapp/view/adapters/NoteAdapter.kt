package com.example.notesapp.view.adapters


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.LockedDialogBinding
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.model.Constants
import com.example.notesapp.model.local.entity.Note
import com.example.notesapp.utils.Common
import com.example.notesapp.view.fragments.NotesFragment
import com.google.android.material.snackbar.Snackbar

class NoteAdapter(private val noteList: List<Note>, val context: Context) :
    RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {
    private lateinit var noteItemBinding: NoteItemBinding
    private lateinit var lockedDialogBinding: LockedDialogBinding
    private lateinit var navController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        noteItemBinding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(noteItemBinding)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(noteList[position])
        holder.itemView.setOnClickListener { v ->
            val locked = noteList[position].isLocked.toBoolean()
            noteList[position].isLocked = locked.toString()
            if (locked) {
                showLockedNoteDialog(noteList[position], position)
            } else {
                val bundle = Bundle()
                bundle.putParcelable("note",noteList[position])
                navController = holder.itemView.findNavController()
                navController.navigate(R.id.action_dashboardFragment3_to_notesFragment3,bundle)
            }
        }
        holder.itemView.setOnLongClickListener { v ->
            showLongClicked(noteList[position], position)
            true
        }
        noteItemBinding.apply {
            imgFavorite.setOnClickListener {
                var starred: Boolean = noteList[position].isStarred.toBoolean()
                starred = !starred
                noteList[position].isStarred = starred.toString()
                if (starred) {
                    imgFavorite.setImageResource(R.drawable.baseline_star_24)
                } else {
                    imgFavorite.setImageResource(R.drawable.favorite_star_24)
                }
            }

        }

    }

    private fun getBundleData(position: Int): NotesFragment {
        val data = Bundle()
        data.putParcelable("note", noteList[position])
        val notesFragment = NotesFragment()
        notesFragment.arguments = data
        return notesFragment
    }


    inner class NotesViewHolder(val view: NoteItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(note: Note) {
            note.apply {
                view.apply {
                    txtNoteTitle.text = title
                    txtNoteBody.text = body

                    txtDate.text = date
                    if (isStarred == "true") {
                        imgFavorite.setImageResource(R.drawable.favorite_star_24)
                    } else {
                        imgFavorite.setImageResource(R.drawable.baseline_star_24)
                    }
                    if (isLocked == "true") {
                        imgLock.setImageResource(R.drawable.baseline_lock_24)
                    } else {
                        imgLock.setImageResource(R.drawable.baseline_lock_open_24)
                    }
                    mainCard.setBackgroundColor(
                        Color.parseColor(
                            Constants.STRONG_COLORS[Common.stringInt(
                                note.bgColor,
                                true
                            )]
                        )
                    )
                }
            }
        }
    }

    private fun showLockedNoteDialog(note: Note, index: Int) {
        lockedDialogBinding =
            LockedDialogBinding.inflate(LayoutInflater.from(context), noteItemBinding.root, false)
        val view = lockedDialogBinding.root
        val builder = AlertDialog.Builder(context).apply {
            setView(view)
            setTitle("Passcode Required")
            setPositiveButton("View") { d, _ ->
                val dialogPasscode = lockedDialogBinding.edPassword.text.toString()
                if (dialogPasscode != note.passcode) {
                    showIncorrectCodeMsg()
                } /*else {
                    d.dismiss()
                    val activity = view.context as AppCompatActivity
                    activity.supportFragmentManager.beginTransaction()
                        .add(R.id.dashboardFragment, getBundleData(index))
                        .addToBackStack(MainActivity.TAG_NOTES).commit()
                }*/
                lockedDialogBinding.edPassword.text?.clear()
            }
            setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
        }
        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }
        builder.show()
    }

    private fun showIncorrectCodeMsg() {
        Snackbar.make(
            noteItemBinding.root,
            "Incorrect passcode",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showLongClicked(note: Note, index: Int) {
        val view = lockedDialogBinding.root
        val builder = AlertDialog.Builder(context).apply {
            setView(view)
            setTitle("Delete")
            setMessage("Are you sure to Delete?")
            setPositiveButton("Yes") { d, _ ->

                d.dismiss()
            }
            setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
            }
        }
        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }
        builder.show()
    }


}