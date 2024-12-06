package com.example.roomxmlviewsexperiment.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.roomxmlviewsexperiment.R
import com.example.roomxmlviewsexperiment.databinding.FragmentAddBinding
import com.example.roomxmlviewsexperiment.model.User
import com.example.roomxmlviewsexperiment.viewmodel.UserViewModel

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)

        binding.btAdd.setOnClickListener {
            addDataToDatabase()
        }

        return binding.root
    }

    private fun addDataToDatabase() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val age = binding.etAge.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty()) {
            userViewModel.addUser(
                User(
                    0,
                    firstName,
                    lastName,
                    age.toInt()
                )
            )
            Toast.makeText(requireContext(), "Successfully added user.", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }


    }

}