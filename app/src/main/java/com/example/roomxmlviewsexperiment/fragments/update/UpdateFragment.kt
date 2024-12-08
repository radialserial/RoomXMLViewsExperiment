package com.example.roomxmlviewsexperiment.fragments.update

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomxmlviewsexperiment.R
import com.example.roomxmlviewsexperiment.databinding.FragmentUpdateBinding
import com.example.roomxmlviewsexperiment.model.user.User
import com.example.roomxmlviewsexperiment.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater)

        try {
            CoroutineScope(Dispatchers.Main).launch {
                currentUser = userViewModel.getUserById(args.currentUserId).await()
                updateUserDisplay(currentUser)
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserDisplay(currentUser: User) {
        with(binding) {
            etFirstName.setText(currentUser.firstName)
            etLastName.setText(currentUser.lastName)
            etAge.setText(currentUser.age.toString())

            btUpdate.setOnClickListener {
                if (etFirstName.text.isNotEmpty() && etLastName.text.isNotEmpty() && etAge.text.isNotEmpty()) {
                    val updatedUser = User(
                        currentUser.id,
                        etFirstName.text.toString(),
                        etLastName.text.toString(),
                        etAge.text.toString().toInt()
                    )
                    userViewModel.upsertUser(updatedUser)
                    Toast.makeText(requireContext(), "Updated user.", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill out all fields.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            showDeleteUserDialog()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            return true
        }
        return false
    }

    private fun showDeleteUserDialog() {
        AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                deleteUser()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Deleting ${currentUser.firstName}")
            .setMessage("Are you sure you want to delete ${currentUser.firstName}?")
            .create().show()
    }

    private fun deleteUser() {
        userViewModel.deleteUser(currentUser)
        Toast.makeText(
            requireContext(),
            "Successfully deleted ${currentUser.firstName}.",
            Toast.LENGTH_LONG
        ).show()
    }

}