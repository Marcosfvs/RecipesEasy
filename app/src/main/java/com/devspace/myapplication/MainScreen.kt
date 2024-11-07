package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreen(navController: NavHostController) {
    var randomRecipes by rememberSaveable { mutableStateOf<List<RecipeDTO>>(emptyList()) }
    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    val callRandomRecipes = apiService.getRandomRecipes()

    callRandomRecipes.enqueue(object : Callback<RecipesResponse> {
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
        recipesList = randomRecipes,

        ) { itemClicked ->

    }
}

@Composable
private fun RecipesList(
    recipesList: List<RecipeDTO>,
    onClick: (RecipeDTO) -> Unit
) {
    LazyColumn {
        items(recipesList) {
            RecipesItem(
                recipesDTO = it,
                onClick = onClick
            )
        }
    }
}


@Composable
private fun RecipesItem(
    recipesDTO: RecipeDTO,
    onClick: (RecipeDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                onClick.invoke(recipesDTO)
            }
    ) {
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            text = recipesDTO.title
        )
        AsyncImage(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = recipesDTO.image,
            contentDescription = "${recipesDTO.title}Recipe Image"
        )
        Text(
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            text = recipesDTO.summary
        )
    }
}