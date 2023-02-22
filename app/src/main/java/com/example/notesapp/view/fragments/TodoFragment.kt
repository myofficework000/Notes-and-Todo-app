package com.example.notesapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentDashboardBinding
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.databinding.FragmentTodoBinding

class TodoFragment : Fragment() {
    private lateinit var binding: FragmentTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

}