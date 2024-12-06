package com.example.roomxmlviewsexperiment.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomxmlviewsexperiment.databinding.UserItemBinding
import com.example.roomxmlviewsexperiment.model.User

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var userList = emptyList<User>()

    inner class ViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(binding) {
                tvId.text = user.id.toString()
                tvFirstName.text = user.firstName
                tvLastName.text = user.lastName
                tvAge.text = "(${user.age})"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.bind(currentItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }

}