package com.example.recipebook.data.source.remote

import com.example.recipebook.data.model.TechRecipe
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RecipeApiService {
    @GET("/top-films")
    suspend fun getTopFilms(): Response<List<TechRecipe>>

    companion object {
        const val BASE_URL = "http://192.168.0.107:21100/"

        fun create(): RecipeApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RecipeApiService::class.java)
        }
    }
}
