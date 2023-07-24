package com.alpaca.hyperpong.presentation.screens.login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.navigation.Screen
import com.alpaca.hyperpong.presentation.common.FormularioDadosUsuario
import com.alpaca.hyperpong.presentation.common.RichTooltipGenerico

@Composable
fun LoginContent(
    navController: NavHostController,
    onButtonClicked: (email: String, senha: String) -> Unit
) {
    Box(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.ic_bolt_sharp),
            contentDescription = "Ícone Relâmpago"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                RichTooltipGenerico(
                    titulo = "Faça parte do time campeão",
                    descricao = "Faça login ou crie uma conta para poder se matricular nas nossas aulas, participar de eventos e ficar por dentro das últimas novidades!",
                    textoAcao = "Saiba mais",
                    onActionClicked = {}
                )
            }
            FormularioDadosUsuario(tituloBotao = "Fazer Login") { email, senha ->
                onButtonClicked(
                    email,
                    senha
                )
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
                    navController
                    navController.navigate(Screen.Register.route)
                }) {
                    Text(text = "Registre-se")
                }
            }
        }
    }
}