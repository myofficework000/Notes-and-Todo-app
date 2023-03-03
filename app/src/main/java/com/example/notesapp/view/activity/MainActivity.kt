package com.example.notesapp.view.activity

import  android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.databinding.FragmentDashboardBinding
import com.example.notesapp.view.fragments.NotesFragment
import com.example.notesapp.view.fragments.TodoDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var floatingBtnVisible = false
    private var fragmentManger = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openTodoFragment(){
        TodoDialog().show(supportFragmentManager,"TodoDialog")
    }
    fun openNoteFragment(){
        fragmentManger.beginTransaction()
            .add(R.id.dashboardFragment, NotesFragment())
            .addToBackStack(MainActivity.TAG_NOTES)
            .commit()
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
    companion object {
        const val TAG_NOTES = "Notes Fragment"
    }
}