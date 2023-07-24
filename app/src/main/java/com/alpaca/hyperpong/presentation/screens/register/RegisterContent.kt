package com.alpaca.hyperpong.presentation.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.presentation.common.FormularioDadosUsuario

@Composable
fun RegisterContent(
    navController: NavHostController,
    onButtonClicked: (email: String, senha: String) -> Unit
) {
    var nome by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            colorFilter = ColorFilter.tint(color = Color.White),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.ic_bolt_sharp),
            contentDescription = "Ícone Relâmpago"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Nome") },
                placeholder = { Text(text = "Opcional") },
                singleLine = true,
                value = nome,
                onValueChange = { nome = it }
            )
            FormularioDadosUsuario(tituloBotao = "Registrar") { email, senha ->
                onButtonClicked(email, senha)
            }
            Text(
                text = "Ou entre com",
                color = MaterialTheme.colorScheme.onSurface
            )
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Continuar com a conta Google")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Não tem uma conta?",
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = {
                    navController.navigate(Screen.Login.route)
                }) {
                    Text(text = "Entrar")
                }
            }
        }
    }
}