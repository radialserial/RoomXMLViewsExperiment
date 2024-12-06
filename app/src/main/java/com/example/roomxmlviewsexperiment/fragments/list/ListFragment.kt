package com.example.roomxmlviewsexperiment.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomxmlviewsexperiment.R
import com.example.roomxmlviewsexperiment.databinding.FragmentListBinding
import com.example.roomxmlviewsexperiment.viewmodel.UserViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        binding.fabAdd.setOnClickListener() {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        val adapter = ListAdapter()
        with(binding.rvUserList) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        userViewModel.usersData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        return binding.root
    }

}