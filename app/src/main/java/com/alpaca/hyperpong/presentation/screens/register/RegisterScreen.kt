package com.alpaca.hyperpong.presentation.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alpaca.hyperpong.presentation.shared.RegisterContent
import com.alpaca.hyperpong.presentation.shared.AuthViewModel
import com.alpaca.hyperpong.util.Response

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onSignInClicked: () -> Unit,
    onSignedUp: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val authResponse by authViewModel.response.collectAsStateWithLifecycle()
    val loadingRequest by remember { derivedStateOf { authResponse is Response.Loading } }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        RegisterContent(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            isLoading = loadingRequest,
            onSignInClicked = { onSignInClicked() }
        ) { user ->
            authViewModel.signUp(user = user)
        }
    }

    LaunchedEffect(authResponse) {
        when (val response = authResponse) {
            is Response.Success -> {
                if (response.data) {
                    snackbarHostState.showSnackbar("Cadastro realizado com sucesso!")
                    onSignedUp()
                } else {
                    println()
                    //TODO: REGISTROU MAS NÃO SALVOU NO BANCO
                }
            }

            is Response.Error -> snackbarHostState.showSnackbar(response.error)

            Response.Idle -> {}
            Response.Loading -> {}
        }
    }
}