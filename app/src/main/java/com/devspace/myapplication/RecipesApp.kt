package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.detail.presentation.ui.RecipesDetailScreen
import com.devspace.myapplication.list.presentation.RecipesListViewModel
import com.devspace.myapplication.list.presentation.ui.MainScreen
import com.devspace.myapplication.search.presentation.ui.SearchRecipesScreen

@Composable
fun RecipesApp(
    listViewModel: RecipesListViewModel,
    detailViewModel: DetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding_screen") {
        composable(
            route = "onboarding_screen"
        ) {
            OnbordingScreen(navController)
        }
        composable(
            route = "main_screen"
        ) {
            MainScreen(navController,listViewModel)
        }

        composable(
            route = "recipeDetail" + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("id"))
            RecipesDetailScreen(id, navController, detailViewModel)
        }
        composable(
            route = "search_screen" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val query = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(query, navController)

        }
    }
}