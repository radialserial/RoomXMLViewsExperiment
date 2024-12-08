package com.example.roomxmlviewsexperiment.model.user

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val usersData: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

}