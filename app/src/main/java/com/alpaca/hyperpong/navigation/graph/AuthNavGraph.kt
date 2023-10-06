package com.alpaca.hyperpong.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alpaca.hyperpong.navigation.AuthScreen
import com.alpaca.hyperpong.presentation.screens.login.LoginScreen
import com.alpaca.hyperpong.presentation.screens.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onSignUpClicked = {
                    navController.navigate(route = AuthScreen.Register.route)
                },
                onAuthenticaded = {
                    navController.navigate(route = Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthScreen.Register.route) {
            RegisterScreen(
                onSignInClicked = {
                    navController.popBackStack()
                },
                onSignedUp = {
                    navController.navigate(route = Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}