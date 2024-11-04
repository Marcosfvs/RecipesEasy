package com.devspace.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

                    ){ itemCliked ->

                    }


                }
            }
        }
    }
}


@Composable
fun RecipesList(
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
fun RecipesItem(
    recipesDTO: RecipeDTO,
    onClick: (RecipeDTO) -> Unit
) {
    Column(modifier = Modifier
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
                .padding(all = 4.dp)
                .width(120.dp)
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
