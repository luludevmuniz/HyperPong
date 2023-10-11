package com.alpaca.hyperpong.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.presentation.common.RichTooltipGenerico
import com.alpaca.hyperpong.ui.theme.HyperPongTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onSignUpClicked: () -> Unit,
    onSignInClicked: (String, String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    var isSenhaVisivel by rememberSaveable { mutableStateOf(false) }
    var isEmailComErro by rememberSaveable { mutableStateOf(false) }
    var isSenhaComErro by rememberSaveable { mutableStateOf(false) }
    val (focusRequester) = FocusRequester.createRefs()

    Box(modifier = modifier) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            contentScale = ContentScale.FillHeight,
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
            RichTooltipGenerico(
                titulo = "Faça parte do time campeão",
                descricao = "Faça login ou crie uma conta para poder se matricular nas nossas aulas, participar de eventos e ficar por dentro das últimas novidades!",
                textoAcao = "Saiba mais",
                onActionClicked = {}
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    modifier = Modifier
                        .widthIn(max = 488.dp)
                        .fillMaxWidth(),
                    value = email,
                    onValueChange = { inputText ->
                        isEmailComErro = inputText.isBlank()
                        email = inputText
                    },
                    label = {
                        Text(text = "E-mail")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (email.isBlank()) {
                                isEmailComErro = true
                            } else {
                                focusRequester.requestFocus()
                            }
                        }
                    ),
                    isError = isEmailComErro,
                    supportingText = {
                        if (isEmailComErro) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "E-mail inválido",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (isEmailComErro) {
                            painterResource(id = R.drawable.ic_error_filled)
                            val description = "E-mail Inválido"
                            IconButton(onClick = { }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_error_filled),
                                    description
                                )
                            }
                        }
                    },
                )
                OutlinedTextField(
                    modifier = Modifier
                        .widthIn(max = 488.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester),
                    value = senha,
                    onValueChange = { inputText ->
                        isSenhaComErro = inputText.isBlank()
                        senha = inputText
                    },
                    label = {
                        Text(text = "Senha")
                    },
                    visualTransformation = if (isSenhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    isError = isSenhaComErro,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        val image =
                            if (isSenhaComErro) painterResource(id = R.drawable.ic_error_filled)
                            else if (isSenhaVisivel) painterResource(id = R.drawable.ic_visibility)
                            else painterResource(id = R.drawable.ic_visibility_off)

                        val description = if (isSenhaComErro) "error"
                        else if (isSenhaVisivel) "Esconder senha"
                        else "Mostrar senha"

                        IconButton(onClick = { isSenhaVisivel = !isSenhaVisivel }) {
                            Icon(painter = image, description)
                        }
                    },
                    supportingText = {
                        if (isSenhaComErro) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Senha inválida",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    modifier = Modifier
                        .widthIn(max = 320.dp)
                        .fillMaxWidth(),
                    enabled = !isLoading,
                    onClick = {
                        isEmailComErro = email.isBlank()
                        isSenhaComErro = senha.isBlank()
                        if (!isEmailComErro && !isSenhaComErro) {
                            onSignInClicked(email, senha)
                        }
                    }
                ) {
                    Text(text = "Entrar")
                }
            }

            Row(
                modifier = Modifier
                    .widthIn(max = 488.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(0.3f),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.weight(0.20f),
                    textAlign = TextAlign.Center,
                    text = "Ou",
                    color = MaterialTheme.colorScheme.onSurface
                )
                HorizontalDivider(
                    modifier = Modifier.weight(0.3f),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            OutlinedButton(
                modifier = Modifier
                    .widthIn(max = 320.dp)
                    .fillMaxWidth(),
                onClick = { }
            ) {
                Text(text = "Continuar como convidado")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Não tem uma conta?",
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { onSignUpClicked() }) {
                    Text(text = "Registre-se")
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginContentPrev() {
    HyperPongTheme {
        LoginContent(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
            isLoading = false,
            onSignInClicked = { _, _ -> },
            onSignUpClicked = {})
    }
}