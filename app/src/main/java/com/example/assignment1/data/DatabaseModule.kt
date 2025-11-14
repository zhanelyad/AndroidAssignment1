package com.example.assignment1.data

import android.content.Context
import androidx.room.Room

object DatabaseModule {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}