package com.devspace.myapplication.retrofit.searchmodel

data class SearchRecipesResponse(
    val results: List<SearchRecipeDTO>
)

data class SearchRecipeDTO(
    val id: Int,
    val title: String,
    val image: String
)
