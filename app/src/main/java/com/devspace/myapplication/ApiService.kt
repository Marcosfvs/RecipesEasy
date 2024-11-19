package com.devspace.myapplication

import com.devspace.myapplication.retrofit.recipesmodel.RecipeDTO
import com.devspace.myapplication.retrofit.recipesmodel.RecipesResponse
import com.devspace.myapplication.retrofit.searchmodel.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipesResponse>

    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeInformation(@Path("id") id: String): Call<RecipeDTO>

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}