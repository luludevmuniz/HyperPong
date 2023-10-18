package com.alpaca.hyperpong.presentation.screens.detalhes_evento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val paymentUrlResponse by viewModel.paymentUrl.collectAsStateWithLifecycle()
    var paymentUrl by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(paymentUrlResponse) {
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
    }
    when (val event = eventResponse) {
        is Success -> AboutEventContent(
            //TODO: Refatorar para não opcional
            event = event.data!!,
            showDialog = showDialog,
            isLoading = isLoading,
            tomorrowDay = viewModel.getTomorrowDay(),
            onPaymentUrlRequest = { body -> viewModel.getPaymentUrl(body = body) },
            paymentUrl = paymentUrl,
            onDimissDialog = { showDialog = false },
            onNavigationIconClicked = { onNavigationIconClicked() }
        )

        is Error -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        )

        is Loading -> CircularProgressIndicator()
        is Idle -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Cyan)
        )
    }
}