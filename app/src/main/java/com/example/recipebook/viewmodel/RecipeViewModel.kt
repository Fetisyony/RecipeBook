package com.example.recipebook.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.data.model.Recipe
import com.example.recipebook.data.repository.RecipeRepository
import com.example.recipebook.data.source.remote.RecipeApiService
import com.example.recipebook.data.source.remote.downloadImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@SuppressLint("StaticFieldLeak")
class RecipeViewModel(private var repository: RecipeRepository, private val context: Context) : ViewModel() {
    private val _recipeList = MutableStateFlow<List<Recipe>>(emptyList())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    private val recipeApi = RecipeApiService.create()

    init {
        fetchRecipesFromApi()
    }

    private fun fetchRecipesFromApi() {
        viewModelScope.launch {
            try {
                repository.loadRecipesFromApi(recipeApi)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                loadRecipesFromDb()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadRecipesFromDb() {
        viewModelScope.launch {
            repository.loadAllRecipesFromDb().collect {
                it -> _recipeList.value = it.map {
                    val uri: Uri? = downloadImage(context, it.imageUrl)
                    Recipe(it.id, it.name, it.description, uri, it.imageUrl)
                }
            }
        }
    }
}