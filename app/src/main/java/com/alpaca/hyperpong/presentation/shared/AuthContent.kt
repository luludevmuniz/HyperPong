package com.alpaca.hyperpong.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R

@Composable
fun AuthContent(
    modifier: Modifier = Modifier,
    loadingRequest: Boolean,
    logoColor: Color,
    topContent: @Composable () -> Unit,
    buttonText: String,
    bottomText: String,
    bottomTextButton: String,
    onBottomTextButtonClicked: () -> Unit,
    onButtonClicked: (email: String, senha: String) -> Unit
) {
    Box(modifier = modifier) {
        if (loadingRequest) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            colorFilter = ColorFilter.tint(logoColor),
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
            topContent()
            FormularioDadosUsuario(
                tituloBotao = buttonText,
                buttonEnabled = !loadingRequest
            ) { email, senha ->
                onButtonClicked(email, senha)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(text = "Continuar com a conta Google")
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(text = "Continuar como convidado")
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 12.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bottomText,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { onBottomTextButtonClicked() }) {
                    Text(text = bottomTextButton)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun FormularioDadosUsuario(
    tituloBotao: String,
    buttonEnabled: Boolean,
    onButtonClicked: (email: String, senha: String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    var isSenhaVisivel by rememberSaveable { mutableStateOf(false) }
    var isEmailComErro by rememberSaveable { mutableStateOf(false) }
    var isSenhaComErro by rememberSaveable { mutableStateOf(false) }
    val (focusRequester) = FocusRequester.createRefs()

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { inputText ->
                isEmailComErro = inputText.isBlank()
                email = inputText
            },
            label = {
                Text(text = "E-mail")
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
            trailingIcon = {
                val image = if (isSenhaComErro) painterResource(id = R.drawable.ic_error_filled)
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
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnabled,
            onClick = {
                isEmailComErro = email.isBlank()
                isSenhaComErro = senha.isBlank()
                if (!isEmailComErro && !isSenhaComErro) {
                    onButtonClicked(email, senha)
                }
            }
        ) {
            Text(text = tituloBotao)
        }
    }
}