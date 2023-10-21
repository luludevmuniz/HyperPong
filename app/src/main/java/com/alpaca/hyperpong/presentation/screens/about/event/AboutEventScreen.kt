package com.alpaca.hyperpong.presentation.screens.about.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.firestore.Category
import com.alpaca.hyperpong.domain.model.firestore.Event
import com.alpaca.hyperpong.presentation.common.LoadingScreen
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Loading
import com.alpaca.hyperpong.util.Response.Success

@Composable
fun AboutEventScreen(
    viewModel: AboutEventViewModel = hiltViewModel(),
    eventoId: String,
    onNavigationIconClicked: () -> Unit
) {
    viewModel.getEvento(id = eventoId)
    val eventResponse by viewModel.event.collectAsStateWithLifecycle()
    var event: Event? by remember { mutableStateOf(null) }
    val paymentUrlResponse by viewModel.paymentUrl.collectAsStateWithLifecycle()
    var paymentUrl by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val isLoading by remember {
        derivedStateOf {
            eventResponse is Loading || paymentUrlResponse is Loading
        }
    }
    var openBottomSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedCategory by remember { mutableStateOf(event?.categories?.first() ?: Category()) }
    val uriHandler = LocalUriHandler.current

    if (eventResponse is Success) {
        event = (eventResponse as Success<Event>).data
        selectedCategory = event?.categories?.first() ?: Category()
    }

    if (paymentUrlResponse is Success) {
        paymentUrl = (paymentUrlResponse as Success<String>).data
        showDialog = true
    }
    if (isLoading) {
        LoadingScreen()
    } else if (eventResponse is Error) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
                Text(
                    text = "Let",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Text(text = "Parece que escondemos a bolinha com o corpo na hora de sacar a página que você esperava ver.\nPor favor, comunique o juiz ou aos professores!")
            Row(
                modifier = Modifier.padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Voltar",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Entrar em contato",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onErrorContainer),
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.ic_bolt_sharp),
            contentDescription = "Ícone Relâmpago"
        )
    } else {
        Scaffold(
            topBar = {
                TopBarPadrao(titulo = event?.title.orEmpty()) {
                    onNavigationIconClicked()
                }
            },
            floatingActionButton = {
                if (selectedCategory.participants.size < selectedCategory.max_participants) {
                    SignUpFab {
                        openBottomSheet = true
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { innerPadding ->

            event?.let {
                AboutEventContent(
                    modifier = Modifier.padding(innerPadding),
                    event = it,
                    selectedCategory = selectedCategory,
                    openBottomSheet = openBottomSheet,
                    tomorrowDay = viewModel.getTomorrowDay(),
                    onPaymentUrlRequest = { body ->
                        if (paymentUrl.isEmpty()) {
                            viewModel.getPaymentUrl(body = body)
                        } else {
                            showDialog = true
                        }
                    },
                    onDimissBottomSheet = { openBottomSheet = false },
                ) { category ->
                    selectedCategory = category
                }
            }

            if (showDialog) {
                AlertDialog(
                    title = {
                        Text(text = stringResource(R.string.title_payment_dialog))
                    },
                    text = {
                        Text(text = stringResource(R.string.message_payment_dialog))
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(text = stringResource(R.string.dismiss_button_text_payment_dialog))
                        }
                    },
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            uriHandler.openUri(paymentUrl)
                            showDialog = false
                        }) {
                            Text(text = stringResource(R.string.confirm_button_text_payment_dialog))
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun SignUpFab(onSignUpClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = {
            Text(text = "Inscrever")
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_square),
                contentDescription = "Se inscrever no evento"
            )
        },
        onClick = { onSignUpClick() }
    )
}