package com.example.recipebook.data.repository

import com.example.recipebook.data.source.local.dao.RecipeDao
import com.example.recipebook.data.source.local.dao.RecipeEntity
import com.example.recipebook.data.source.remote.RecipeApiService
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    suspend fun loadRecipesFromApi(apiService: RecipeApiService) {
        val response = apiService.getTopFilms()

        if (response.isSuccessful && response.body() != null) {
            recipeDao.insertRecipes(response.body()!!.map {
                RecipeEntity(it.id, it.name, it.description, it.imageUrl, null)
            })
        }
    }

    suspend fun loadAllRecipesFromDb(): Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }
}
