package com.example.roomxmlviewsexperiment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomxmlviewsexperiment.model.AppDatabase
import com.example.roomxmlviewsexperiment.model.user.User
import com.example.roomxmlviewsexperiment.model.user.UserRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) :
    AndroidViewModel(application = application) {

    val usersData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        usersData = repository.usersData
    }

    fun upsertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertUser(user)
        }
    }

    fun getUserById(userId: Int): Deferred<User> {
        return viewModelScope.async(Dispatchers.IO) {
            repository.getUserById(userId)
        }
    }
}