package com.example.roomxmlviewsexperiment.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomxmlviewsexperiment.model.user.User
import com.example.roomxmlviewsexperiment.model.user.UserDao

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var _instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // Needs a temp val otherwise compiler gets angry
            val tempInstance = _instance

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                _instance = instance
                return instance
            }
        }

    }

}