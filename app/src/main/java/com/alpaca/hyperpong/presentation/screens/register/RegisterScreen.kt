package com.alpaca.hyperpong.presentation.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alpaca.hyperpong.presentation.shared.AuthContent
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
    var nome by rememberSaveable { mutableStateOf("") }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        AuthContent(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            loadingRequest = loadingRequest,
            logoColor = MaterialTheme.colorScheme.onSecondary,
            buttonText = "Registrar",
            bottomText = "Já tem uma conta?",
            bottomTextButton = "Entrar",
            topContent = {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Nome") },
                    placeholder = { Text(text = "Opcional") },
                    singleLine = true,
                    value = nome,
                    onValueChange = { nome = it }
                )
            },
            onBottomTextButtonClicked = { onSignInClicked() }
        ) { email, senha ->
            authViewModel.registrarUsuarioComEmailESenha(email = email, senha = senha)
        }
    }

    LaunchedEffect(authResponse) {
        when (val response = authResponse) {
            is Response.Success -> {
                snackbarHostState.showSnackbar("Cadastro realizado com sucesso!")
                onSignedUp()
            }

            is Response.Error -> snackbarHostState.showSnackbar(response.error)

            Response.Idle -> {}
            Response.Loading -> {}
        }
    }
}