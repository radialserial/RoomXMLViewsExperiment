package com.example.roomxmlviewsexperiment.model.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    fun upsertUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): User?

}