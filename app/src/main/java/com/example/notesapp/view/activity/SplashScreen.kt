package com.example.notesapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slideAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in_zoom_out)

        binding.notes.apply {
            startAnimation(slideAnim)
        }

    }
}