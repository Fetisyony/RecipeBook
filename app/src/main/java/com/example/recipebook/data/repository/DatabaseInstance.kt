package com.example.recipebook.data.repository

import android.content.Context
import androidx.room.Room
import com.example.recipebook.data.source.local.database.RecipeDatabase

object DatabaseProvider {
    private var INSTANCE: RecipeDatabase? = null

    fun getDatabase(context: Context): RecipeDatabase {
        if (INSTANCE == null) {
            synchronized(RecipeDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipes_db"
                ).build()
            }
        }
        return INSTANCE!!
    }
}
