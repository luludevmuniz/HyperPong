package com.alpaca.hyperpong.presentation.screens.about.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.presentation.common.LoadingScreen
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Idle
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
    var isLoading by remember { mutableStateOf(true) }
    var openBottomSheet by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current
    val snackbarHostState = remember { SnackbarHostState() }


    when (val newEvent = eventResponse) {
        is Success -> {
            event = newEvent.data
            isLoading = false
        }

        is Error -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        )

        is Loading -> LoadingScreen()
        is Idle -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Cyan)
        )
    }

    when (val apiResponse = paymentUrlResponse) {
        is Success -> {
            paymentUrl = apiResponse.data
            showDialog = true
            isLoading = false
        }

        is Error -> paymentUrl = apiResponse.error
        Idle -> isLoading = false
        Loading -> isLoading = true
    }


    if (isLoading) {
        LoadingScreen()
    } else {
        Scaffold(
            topBar = {
                TopBarPadrao(titulo = event?.title.orEmpty()) {
                    onNavigationIconClicked()
                }
            },
            floatingActionButton = {
                SignUpFab {
                    openBottomSheet = true
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
                    showDialog = showDialog,
                    isLoading = isLoading,
                    openBottomSheet = openBottomSheet,
                    tomorrowDay = viewModel.getTomorrowDay(),
                    onPaymentUrlRequest = { body ->
                        if (paymentUrl.isEmpty()) {
                            viewModel.getPaymentUrl(body = body)
                        } else {
                            showDialog = true
                        }
                    },
                    paymentUrl = paymentUrl,
                    onDimissDialog = { showDialog = false },
                    onDimissBottomSheet = { openBottomSheet = false },
                    onNavigationIconClicked = { onNavigationIconClicked() }
                )
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