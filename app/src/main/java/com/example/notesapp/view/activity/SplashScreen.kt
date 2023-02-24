package com.example.notesapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slideAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in_zoom_out).apply {
            this.setAnimationListener(object: AnimationListener{
                override fun onAnimationStart(p0: Animation?) {}
                override fun onAnimationRepeat(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    this@SplashScreen.finish()
                }
            })
        }
        //val slideAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in_zoom_out)
        val rightToLeft = AnimationUtils.loadAnimation(this,R.anim.right_to_left)

//        binding.notes.apply {
//            startAnimation(slideAnim)
//        }
        binding.notes.apply {

        }
        binding.pen.apply {
            startAnimation(rightToLeft)
        }

    }
}