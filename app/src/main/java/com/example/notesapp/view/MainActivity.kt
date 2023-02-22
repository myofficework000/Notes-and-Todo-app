package com.example.notesapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.view.fragments.NotesFragment
import com.example.notesapp.view.fragments.TodoFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fragmentManger = supportFragmentManager
    private var floatingBtnVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        binding.apply{
            noteTodoBtn.setOnClickListener {
                if(!floatingBtnVisible){
                    noteBtn.show()
                    todoBtn.show()
                    noteBtn.visibility = View.VISIBLE
                    todoBtn.visibility = View.VISIBLE
                    noteTodoBtn.setImageResource(R.drawable.baseline_floating_close_24)
                    floatingBtnVisible = true
                }else{
                    noteBtn.hide()
                    todoBtn.hide()
                    noteBtn.visibility = View.GONE
                    todoBtn.visibility = View.GONE

                    noteTodoBtn.setImageResource(R.drawable.baseline_add_24)
                    floatingBtnVisible = false
                }
            }

            todoBtn.setOnClickListener {
                fragmentManger.beginTransaction().replace(R.id.dashboardFragment, TodoFragment()).commit()
            }

            noteBtn.setOnClickListener {
                fragmentManger.beginTransaction().replace(R.id.dashboardFragment, NotesFragment()).commit()
            }
        }
    }

}