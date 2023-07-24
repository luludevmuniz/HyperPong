package com.alpaca.hyperpong.presentation.screens.register

import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.presentation.common.AuthViewModel
import com.alpaca.hyperpong.util.RequestState

@Composable
fun RegisterScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val signInRespose = authViewModel.signInRespose.collectAsState()
    RegisterContent(navController) { email, senha ->
        authViewModel.cadastrarUsuario(email = email, senha = senha)
    }

    LaunchedEffect(signInRespose) {
        when(signInRespose.value) {
            is RequestState.Success -> navController.navigate(Screen.Home.route)
            is RequestState.Error -> {

            }
            RequestState.Idle -> TODO()
            RequestState.Loading -> TODO()
        }
    }

    Snackbar() {

    }
}