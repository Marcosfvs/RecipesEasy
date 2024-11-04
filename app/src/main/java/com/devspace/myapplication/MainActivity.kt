package com.devspace.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var randomRecipes by remember { mutableStateOf<List<RecipeDTO>>(emptyList()) }

                    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
                    val callRandomRecipes = apiService.getRandomRecipes()

                    callRandomRecipes.enqueue(object : Callback<RecipesResponse>{
                        override fun onResponse(
                            call: Call<RecipesResponse>,
                            response: Response<RecipesResponse>
                        ) {
                            if (response.isSuccessful) {
                                randomRecipes = response.body()?.recipes ?: emptyList()

                            } else {
                                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                            }
                        }

                        override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
                            Log.d("MainActivity", "Network Error :: ${t.message}")
                        }

                    })

                        RecipesList(
                            recipesList = randomRecipes)


                }
            }
        }
    }
}


@Composable
fun RecipesList(
    recipesList: List<RecipeDTO>
) {
    LazyColumn {
        items(recipesList) {
            RecipesItem(
                recipesDTO = it,
            )
        }
    }
}


@Composable
fun RecipesItem(
    recipesDTO: RecipeDTO,
) {
    Column {
        Text(text = recipesDTO.title)
        AsyncImage(model = recipesDTO.image, contentDescription = "Recipe Image")
        //Text(text = recipesDTO.summary)
    }
}
