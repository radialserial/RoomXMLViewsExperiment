package com.example.roomxmlviewsexperiment.model.user

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val usersData: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

}