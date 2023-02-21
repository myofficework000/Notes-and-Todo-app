package com.example.notesapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.TodoItemBinding
import com.example.notesapp.model.local.entity.Todo

class TodoAdapter(private val todoList: ArrayList<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    private lateinit var todoBinding: TodoItemBinding

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        todoBinding = TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoViewHolder(todoBinding.root)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    inner class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(todo: Todo){
            todo.apply{
                todoBinding.apply{
                    todoItem.setText(title)
                }
            }
        }
    }


}