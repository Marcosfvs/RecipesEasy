package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.designsystem.components.ERHtmlText
import com.devspace.myapplication.designsystem.components.ERSearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

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

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainContent(
            recipes = randomRecipes,
            onSearchClicked = { query ->
                val tempCleanQuery = query.trim()
                if (tempCleanQuery.isNotEmpty()) {
                    navController.navigate(route = "search_recipes/${tempCleanQuery}")
                }
            },
            onClick =
            { itemClicked ->
                navController.navigate(route = "recipeDetail/${itemClicked.id}")

            }
        )
    }
}

@Composable
fun MainContent(
    recipes: List<RecipeDTO>,
    onSearchClicked: (String) -> Unit,
    onClick: (RecipeDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var query by remember {
            mutableStateOf("")
        }
        SearchSession(
            label = "Find best recipes \nfor cooking",
            query = query,
            onValueChange = { newValue ->
                query = newValue
            },
            onSearchClicked = onSearchClicked
        )
        RecipesSession(
            label = "Recipes",
            recipes = recipes,
            onClick = onClick
        )
    }

}

@Composable
fun SearchSession(
    label: String,
    query: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )

    ERSearchBar(
        query = query,
        placeHolder = "Search recipes",
        onValueChange = onValueChange,
        onSearchClicked = {
            onSearchClicked.invoke(query)

        }
    )
}

@Composable
fun RecipesSession(
    label: String,
    recipes: List<RecipeDTO>,
    onClick: (RecipeDTO) -> Unit
) {
    Text(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )
    RecipeList(
        recipesList = recipes,
        onClick = onClick
    )

}

@Composable
private fun RecipeList(
    recipesList: List<RecipeDTO>,
    onClick: (RecipeDTO) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(recipesList) {
            RecipeItem(
                recipesDTO = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecipeItem(
    recipesDTO: RecipeDTO,
    onClick: (RecipeDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick.invoke(recipesDTO)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = recipesDTO.image,
            contentDescription = "${recipesDTO.title}Recipe Image"
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            text = recipesDTO.title
        )

        ERHtmlText(text = recipesDTO.summary, maxLine = 3)
    }
}