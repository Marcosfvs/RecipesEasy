package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlin.reflect.typeOf

@Composable
fun RecipesApp() {
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
            MainScreen(navController)
        }

        composable(
            route = "recipeDetail" + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("id"))
            RecipesDetailScreen(id, navController)
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