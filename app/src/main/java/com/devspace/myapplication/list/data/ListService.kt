package com.devspace.myapplication.list.data

import com.devspace.myapplication.retrofit.recipesmodel.RecipeDTO
import com.devspace.myapplication.retrofit.recipesmodel.RecipesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ListService {
    @GET("recipes/random?number=20")
   suspend fun getRandomRecipes(): Response<RecipesResponse>

}