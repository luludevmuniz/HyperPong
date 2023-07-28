package com.alpaca.hyperpong.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alpaca.hyperpong.presentation.screens.EventDetailsScreen
import com.alpaca.hyperpong.presentation.screens.home.HomeScreen
import com.alpaca.hyperpong.presentation.screens.login.LoginScreen
import com.alpaca.hyperpong.presentation.screens.register.RegisterScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Settings.route) {

        }
        composable(route = Screen.AboutUs.route) {

        }
        composable(route = Screen.AboutLessons.route) {

        }
        composable(route = Screen.AboutEvents.route) {

        }
        composable(route = Screen.EventDetails.route) {
            EventDetailsScreen()
        }
    }
}