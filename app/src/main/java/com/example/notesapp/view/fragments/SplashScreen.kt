package com.example.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in_zoom_out)
        val rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left)

        runBlocking {
            val anim1 = async {
                binding.notes.apply {
                    startAnimation(slideAnim)
                }
            }
            val anim2 = async {
                binding.pen.apply {
                    startAnimation(rightToLeft)
                }
            }
            anim1.await()
            anim2.await()
            findNavController().navigate(R.id.action_splashScreen3_to_dashboardFragment3)
        }
    }
}
