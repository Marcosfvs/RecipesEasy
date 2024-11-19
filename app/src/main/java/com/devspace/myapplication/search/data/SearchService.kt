package com.devspace.myapplication.search.data

import com.devspace.myapplication.retrofit.searchmodel.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}