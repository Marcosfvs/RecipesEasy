package com.devspace.myapplication.detail.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ApiService
import com.devspace.myapplication.retrofit.recipesmodel.RecipeDTO
import com.devspace.myapplication.designsystem.components.ERHtmlText
import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.retrofit.data.RetrofitClient
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipesDetailScreen(
    id: String,
    navHostController: NavHostController,
    viewModel: DetailViewModel
) {
    val recipeDTO by viewModel.uidetailrecipe.collectAsState()
    viewModel.fecthRecipeDetail(id)

    recipeDTO?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button"
                    )

                }

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.title
                )
            }
            RecipesDetailContent(it)
        }
    }
}

@Composable
private fun RecipesDetailContent(recipe: RecipeDTO) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title}Recipe Image"
        )

        ERHtmlText(
            text = recipe.summary
        )
    }


}

@Composable
private fun RecipesDetailPreview() {
    EasyRecipesTheme {
        val recipe = RecipeDTO(
            id = 9,
            title = "Title",
            image = "image",
            summary = "Long overview recipe Long overview recipe" +
                    "Long overview recipe Long overview recipe" +
                    "Long overview recipe Long overview recipe"
        )
        RecipesDetailContent(recipe = recipe)
    }
}