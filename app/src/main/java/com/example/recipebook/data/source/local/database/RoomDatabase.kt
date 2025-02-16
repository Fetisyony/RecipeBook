package com.example.recipebook.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.recipebook.data.source.local.dao.Converters
import com.example.recipebook.data.source.local.dao.RecipeDao
import com.example.recipebook.data.source.local.dao.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
