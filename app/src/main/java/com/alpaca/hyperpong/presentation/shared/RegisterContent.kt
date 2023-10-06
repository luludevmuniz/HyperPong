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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.User
import com.alpaca.hyperpong.presentation.common.RichTooltipGenerico

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onSignInClicked: () -> Unit,
    onSignUpClicked: (User) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var document by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isSenhaVisivel by rememberSaveable { mutableStateOf(false) }
    var isDocumentValid by rememberSaveable { mutableStateOf(true) }
    var isPhoneValid by rememberSaveable { mutableStateOf(true) }
    var isNameBlank by rememberSaveable { mutableStateOf(false) }
    val isNameValid by remember { derivedStateOf { !isNameBlank } }
    var isEmailBlank by rememberSaveable { mutableStateOf(false) }
    val isEmailValid by remember { derivedStateOf { !isEmailBlank } }
    var isPasswordEmpty by rememberSaveable { mutableStateOf(false) }
    val isPasswordValid by remember { derivedStateOf { !isPasswordEmpty } }
    val (documentFocusRequester, phoneFocusRequester, emailFocusRequester, passwordFocusRequest) = FocusRequester.createRefs()

    Box(modifier = modifier) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
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
                space = 4.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RichTooltipGenerico(
                titulo = "Faça parte do time campeão",
                descricao = "Crie uma conta para poder participar de eventos, torneios e ficar por dentro das últimas novidades!",
                textoAcao = "Saiba mais",
                onActionClicked = {}
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Nome") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )
                },
                isError = !isNameValid,
                trailingIcon = {
                    if (!isNameValid) {
                        painterResource(id = R.drawable.ic_error_filled)
                        val description = "Nome Inválido"
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_error_filled),
                                description
                            )
                        }
                    }
                },
                value = name,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        documentFocusRequester.requestFocus()
                    }
                ),
                onValueChange = {
                    name = it
                    isNameBlank = name.isBlank()
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(focusRequester = documentFocusRequester),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    label = { Text(text = "CPF") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                    },
                    singleLine = true,
                    value = document,
                    isError = !isDocumentValid,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            phoneFocusRequester.requestFocus()
                        }
                    ),
                    onValueChange = { inputText ->
                        if (inputText.length <= 11) {
                            document = inputText
                            if (inputText.length == 11) {
                                isDocumentValid = true
                            }
                        }
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f)
                        .focusRequester(focusRequester = phoneFocusRequester),
                    label = { Text(text = "Telefone") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    isError = !isPhoneValid,
                    value = phone,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            emailFocusRequester.requestFocus()
                        }
                    ),
                    onValueChange = {inputText ->
                        if (inputText.length <= 11) {
                            phone = inputText
                            if (inputText.length == 11) {
                                isPhoneValid = true
                            }
                        }
                    }
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(focusRequester = emailFocusRequester),
                value = email,
                onValueChange = { inputText ->
                    email = inputText
                    isEmailBlank = email.isBlank()
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
                        passwordFocusRequest.requestFocus()
                    }
                ),
                isError = !isEmailValid,
                trailingIcon = {
                    if (!isEmailValid) {
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
                    .focusRequester(focusRequester = passwordFocusRequest),
                value = password,
                onValueChange = { inputText ->
                    password = inputText
                    isPasswordEmpty = password.isEmpty()
                },
                label = {
                    Text(text = "Senha")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                visualTransformation = if (isSenhaVisivel)
                    VisualTransformation.None else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = !isPasswordValid,
                trailingIcon = {
                    val image = if (!isPasswordValid) painterResource(id = R.drawable.ic_error_filled)
                    else if (isSenhaVisivel) painterResource(id = R.drawable.ic_visibility)
                    else painterResource(id = R.drawable.ic_visibility_off)

                    val description = if (!isPasswordValid) "error"
                    else if (isSenhaVisivel) "Esconder senha"
                    else "Mostrar senha"

                    IconButton(onClick = { isSenhaVisivel = !isSenhaVisivel }) {
                        Icon(painter = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = {
                    isPasswordEmpty = password.isEmpty()
                    isEmailBlank = email.isBlank()
                    isNameBlank = name.isBlank()
                    isPhoneValid = phone.length == 11
                    isDocumentValid = document.length == 11
                    if (isEmailValid && isPasswordValid && !isNameBlank && isDocumentValid && isPhoneValid) {
                        onSignUpClicked(
                            User(
                                document = document,
                                email = email,
                                name = name,
                                phone = phone,
                                password = password
                            )
                        )
                    }
                }
            ) {
                Text(text = "Registrar")
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
                Text(text = "Continuar como convidado")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Já tem uma conta?",
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { onSignInClicked() }) {
                    Text(text = "Entrar")
                }
            }
        }
    }
}