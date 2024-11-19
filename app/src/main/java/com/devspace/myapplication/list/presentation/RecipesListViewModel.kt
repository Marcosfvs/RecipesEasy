package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.list.data.ListService
import com.devspace.myapplication.retrofit.data.RetrofitClient
import com.devspace.myapplication.retrofit.recipesmodel.RecipeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RecipesListViewModel(
    listService: ListService
) : ViewModel() {

    private val _uiRandomRecipes = MutableStateFlow<List<RecipeDTO>>(emptyList())
    val uiRandomRecipes: StateFlow<List<RecipeDTO>> = _uiRandomRecipes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getRandomRecipes()
            if (response.isSuccessful) {
                _uiRandomRecipes.value = response.body()?.recipes ?: emptyList()

            } else {
                Log.d("RecipesListModelView", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return RecipesListViewModel(
                    listService
                ) as T
            }

        }
    }

}