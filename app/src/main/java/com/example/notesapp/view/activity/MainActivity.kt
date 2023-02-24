package com.example.notesapp.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.view.fragments.NotesFragment
import com.example.notesapp.view.fragments.TodoDialog

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
        binding.apply {
            noteTodoBtn.setOnClickListener {
                if (!floatingBtnVisible) {
                    noteBtn.show()
                    todoBtn.show()
                    noteBtn.visibility = View.VISIBLE
                    todoBtn.visibility = View.VISIBLE
                    noteTodoBtn.setImageResource(R.drawable.baseline_floating_close_24)
                    floatingBtnVisible = true
                } else {
                    hideFloatingButton()
                }
            }

            todoBtn.setOnClickListener {
                //fragmentManger.beginTransaction().replace(R.id.dashboardFragment, TodoFragment()).commit()
                TodoDialog().show(supportFragmentManager, "TodoDialog")
            }

            noteBtn.setOnClickListener {
                fragmentManger.beginTransaction()
                    .add(R.id.dashboardFragment, NotesFragment())
                    .addToBackStack(TAG_NOTES)
                    .commit()
                hideFloatingButton()
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun hideFloatingButton() {
        binding.apply {
            noteBtn.hide()
            todoBtn.hide()
            noteBtn.visibility = View.GONE
            todoBtn.visibility = View.GONE
            noteTodoBtn.setImageResource(R.drawable.baseline_add_24)
            floatingBtnVisible = false
        }
    }

    companion object {
        const val TAG_NOTES = "Notes Fragment"
    }
}