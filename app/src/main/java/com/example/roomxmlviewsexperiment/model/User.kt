package com.example.roomxmlviewsexperiment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
)
