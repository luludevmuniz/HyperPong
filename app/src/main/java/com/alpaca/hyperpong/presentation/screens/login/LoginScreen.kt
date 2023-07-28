package com.alpaca.hyperpong.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.presentation.common.RichTooltipGenerico
import com.alpaca.hyperpong.presentation.shared.AuthContent
import com.alpaca.hyperpong.presentation.shared.AuthViewModel
import com.alpaca.hyperpong.util.RequestState

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    Log.d("TESTE", "CHEGOU NA LOGIN SCREEN")
    val snackbarHostState = remember { SnackbarHostState() }
    val signInResponse by authViewModel.signInState.collectAsState()
    val loadingRequest by remember { derivedStateOf { signInResponse is RequestState.Loading } }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        AuthContent(
            modifier = Modifier
                .padding(paddingValues = it)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            loadingRequest = loadingRequest,
            logoColor = MaterialTheme.colorScheme.onPrimary,
            buttonText = "Entrar",
            bottomText = "Não tem uma conta?",
            bottomTextButton = "Registre-se",
            topContent = {
                RichTooltipGenerico(
                    titulo = "Faça parte do time campeão",
                    descricao = "Faça login ou crie uma conta para poder se matricular nas nossas aulas, participar de eventos e ficar por dentro das últimas novidades!",
                    textoAcao = "Saiba mais",
                    onActionClicked = {}
                )
            },
            onBottomTextButtonClicked = { navController.navigate(Screen.Register.route) }
        ) { email, senha ->
            authViewModel.logarUsuario(email = email, senha = senha)
        }
    }

    LaunchedEffect(signInResponse) {
        when (val response = signInResponse) {
            is RequestState.Success -> { navController.navigate(Screen.Home.route) }
            is RequestState.Error -> snackbarHostState.showSnackbar(
                response.t.localizedMessage ?: "Não foi possível fazer login"
            )

            RequestState.Idle -> {}
            RequestState.Loading -> {}
        }
    }
}