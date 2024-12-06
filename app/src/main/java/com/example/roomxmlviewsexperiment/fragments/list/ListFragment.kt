package com.example.roomxmlviewsexperiment.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomxmlviewsexperiment.R
import com.example.roomxmlviewsexperiment.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        binding.fabAdd.setOnClickListener() {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }

}