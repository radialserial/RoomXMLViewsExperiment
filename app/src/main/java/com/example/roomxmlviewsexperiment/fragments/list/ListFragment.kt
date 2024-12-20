package com.example.roomxmlviewsexperiment.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomxmlviewsexperiment.R
import com.example.roomxmlviewsexperiment.databinding.FragmentListBinding
import com.example.roomxmlviewsexperiment.viewmodel.UserViewModel

class ListFragment : Fragment(), MenuProvider {

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

        // Passing this view's nav controller to the list item, since it doesn't have one
        val adapter = ListAdapter(onUserClick = { currentUser ->
            binding.root.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToUpdateFragment(
                    currentUser.id
                )
            )
        })

        with(binding.rvUserList) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        userViewModel.usersData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            showDeleteAllUsersDialog()
            return true
        }
        return false
    }

    private fun showDeleteAllUsersDialog() {
        AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                deleteAllUsers()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Deleting all users")
            .setMessage("Are you sure you want to delete all users?")
            .create().show()
    }

    private fun deleteAllUsers() {
        userViewModel.deleteAllUsers()
    }

}