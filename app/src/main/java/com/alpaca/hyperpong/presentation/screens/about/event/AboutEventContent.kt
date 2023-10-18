package com.alpaca.hyperpong.presentation.screens.about.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.domain.model.cloud_functions.Customer
import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import com.alpaca.hyperpong.presentation.common.ItemIconeTexto
import com.alpaca.hyperpong.presentation.common.TopBarPadrao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutEventContent(
    event: Event,
    showDialog: Boolean,
    isLoading: Boolean,
    tomorrowDay: String,
    onPaymentUrlRequest: (body: GetPaymentUrlRequestBody) -> Unit,
    paymentUrl: String,
    onDimissDialog: () -> Unit,
    onNavigationIconClicked: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopBarPadrao(titulo = event.title) {
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
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 60.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 16.dp
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                ItemIconeTexto(
                                    icone = painterResource(id = R.drawable.ic_money_value),
                                    texto = "Valor: R$ 50,00"
                                )
                                ItemIconeTexto(
                                    icone = painterResource(id = R.drawable.ic_event_day),
                                    texto = "Data: ${event.dataInicioFormatada}"
                                )

                            }
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                ItemIconeTexto(
                                    icone = painterResource(id = R.drawable.ic_subscription_open),
                                    texto = "Participantes: 9/16"
                                )
                                ItemIconeTexto(
                                    icone = painterResource(id = R.drawable.ic_alarm),
                                    texto = "Horário: TODO"
                                )
                            }
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(event.categories) { categoria ->
                                ElevatedSuggestionChip(enabled = false,
                                    onClick = { /*TODO*/ },
                                    colors = ChipColors(
                                        containerColor = SuggestionChipDefaults.elevatedSuggestionChipColors().containerColor,
                                        labelColor = SuggestionChipDefaults.elevatedSuggestionChipColors().labelColor,
                                        leadingIconContentColor = SuggestionChipDefaults.elevatedSuggestionChipColors().leadingIconContentColor,
                                        trailingIconContentColor = SuggestionChipDefaults.elevatedSuggestionChipColors().trailingIconContentColor,
                                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                                        disabledLabelColor = MaterialTheme.colorScheme.onPrimary,
                                        disabledLeadingIconContentColor = SuggestionChipDefaults.elevatedSuggestionChipColors().disabledContainerColor,
                                        disabledTrailingIconContentColor = SuggestionChipDefaults.elevatedSuggestionChipColors().disabledTrailingIconContentColor
                                    ),
                                    label = { Text(text = categoria["name"].toString()) })
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(event.image)
                            .crossfade(true).build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Imagem do evento ${event.title}"
                    )
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = event.description.replace("\\n", " \n "),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (openBottomSheet) {
            EventSignUpModal(sheetState = sheetState, onSignInClicked = {
                val body = GetPaymentUrlRequestBody(
                    value = 1500,
                    payday = tomorrowDay,
                    mainPaymentMethodId = "pix",
                    Customer = Customer(myId = "pay-652937390fb2a5.16130871")
                )
                onPaymentUrlRequest(body)
                openBottomSheet = false
            }) {
                openBottomSheet = false
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = "Prosseguir em seu navegador")
            },
            text = {
                Text(text = "Ao clicar em prosseguir, você será redirecionado para seu navegador padrão, onde poderá finalizar o pagamento.")
            },
            dismissButton = {
                Button(onClick = { onDimissDialog() }) {
                    Text(text = "Cancelar")
                }
            },
            onDismissRequest = { onDimissDialog() },
            confirmButton = {
                Button(onClick = {
                    uriHandler.openUri(paymentUrl)
                }) {
                    Text(text = "Prosseguir")
                }
            }
        )
    }
}

@Composable
fun SignUpFab(onSignUpClick: () -> Unit) {
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