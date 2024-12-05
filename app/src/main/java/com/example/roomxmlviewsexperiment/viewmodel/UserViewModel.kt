package com.example.roomxmlviewsexperiment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomxmlviewsexperiment.model.AppDatabase
import com.example.roomxmlviewsexperiment.model.User
import com.example.roomxmlviewsexperiment.model.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) :
    AndroidViewModel(application = application) {

    private val usersData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        usersData = repository.usersData
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}