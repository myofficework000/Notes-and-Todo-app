package com.example.notesapp.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.model.local.entity.Note
import com.example.notesapp.view.fragments.NotesFragment

class NoteAdpater(private val noteList: List<Note>): RecyclerView.Adapter<NoteAdpater.NotesViewHolder>() {
    private lateinit var noteItemBinding: NoteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        noteItemBinding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(noteItemBinding.root)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(noteList[position])
        val data = Bundle()
        data.putParcelable ("note", noteList[position])
        val notesFragment = NotesFragment()
        notesFragment.arguments = data
        holder.itemView.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?){
                val activity = v!!.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction().replace(R.id.dashboardFragment,notesFragment).commit()
            }
        })
    }

    inner class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(note : Note){
            note.apply {
                noteItemBinding.apply {
                    txtNoteTitle.setText(title)
                    txtNoteBody.setText(body)
                    txtDate.setText(date)
                    if(isStarred == "true"){
                        imgFavorite.setImageResource(R.drawable.favorite_star_24)
                    } else {
                        imgFavorite.setImageResource(R.drawable.baseline_star_24)
                    }
                    if(isLocked == "true"){
                        imgLock.setImageResource(R.drawable.baseline_lock_24)
                    } else {
                        imgLock.setImageResource(R.drawable.baseline_lock_open_24)
                    }
                }
            }
        }
    }
}