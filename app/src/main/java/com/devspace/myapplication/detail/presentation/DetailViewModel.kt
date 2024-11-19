package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.detail.data.DetailService
import com.devspace.myapplication.retrofit.data.RetrofitClient
import com.devspace.myapplication.retrofit.recipesmodel.RecipeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uidetailrecipe = MutableStateFlow<RecipeDTO?>(null)
    val uidetailrecipe: StateFlow<RecipeDTO?> = _uidetailrecipe

    fun fecthRecipeDetail(id: String) {
        if (_uidetailrecipe.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getRecipeInformation(id)
                if (response.isSuccessful) {
                    _uidetailrecipe.value = response.body()
                } else {
                    Log.d("DetailViewModel", "Request Error :: ${response.errorBody()}")
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return DetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}