package com.example.notesapp.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.model.local.entity.Note

class NoteAdpater(private val NoteList: ArrayList<Note>): RecyclerView.Adapter<NoteAdpater.TodoViewHolder>() {
    private lateinit var noteItemBinding: NoteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    }
}