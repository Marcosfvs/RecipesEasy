package com.devspace.myapplication.detail.data

import com.devspace.myapplication.retrofit.recipesmodel.RecipeDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun getRecipeInformation(@Path("id") id: String): Response<RecipeDTO>
}