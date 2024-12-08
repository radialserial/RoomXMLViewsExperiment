package com.example.roomxmlviewsexperiment.model.user

import android.content.res.Resources.NotFoundException
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val usersData: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

    suspend fun getUserById(userId: Int): User {
        return userDao.getUserById(userId) ?: throw NotFoundException("User not found.")
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

}