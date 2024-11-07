package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun RecipesApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding_screen") {
        composable(
            route = "onboarding_screen") {
            OnbordingScreen(navController)
        }
        composable(
            route = "main_screen") {
            MainScreen(navController)
        }
    }
}