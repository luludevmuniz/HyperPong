package com.alpaca.hyperpong.presentation.screens.detalhes_evento

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.cloud_functions.Customer
import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import com.alpaca.hyperpong.presentation.common.ItemIconeTexto
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import com.alpaca.hyperpong.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesEventoScreen(
    viewModel: DetalhesEventoViewModel = hiltViewModel(),
    eventoId: String,
    onNavigationIconClicked: () -> Unit
) {
    viewModel.getEvento(id = eventoId)
    val evento by viewModel.evento.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val response by viewModel.response.collectAsStateWithLifecycle()
    var paymentUrl by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(response) {
        when (val apiResponse = response) {
            is Response.Success -> {
                paymentUrl = apiResponse.data
                showDialog = true
                isLoading = false
            }

            is Response.Error -> paymentUrl = apiResponse.error
            Response.Idle -> isLoading = false
            Response.Loading -> isLoading = true
        }
    }

    Scaffold(topBar = {
        TopBarPadrao(titulo = evento?.nome.orEmpty()) {
            onNavigationIconClicked()
        }
    }, floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text(text = "Inscrever") }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_square),
                contentDescription = "Se inscrever no evento"
            )
        }, onClick = { openBottomSheet = true })
    }, snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
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
                                bottomStart = 20.dp, bottomEnd = 20.dp
                            )
                        )
                        .padding(
                            start = 24.dp, end = 24.dp, bottom = 16.dp
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        verticalArrangement = Arrangement.spacedBy(11.dp)
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
                                    texto = "Data: ${evento?.dataInicioFormatada}"
                                )

                            }
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                ItemIconeTexto(
                                    icone = painterResource(id = R.drawable.ic_subscription_open),
                                    texto = "Participantes: 9/16"
                                )
                                ItemIconeTexto(
                                    icone = painterResource(id = R.drawable.ic_alarm),
                                    texto = "Horário: ${evento?.hora}"
                                )
                            }
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(evento?.categorias.orEmpty()) { categoria ->
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
                                    label = { Text(text = categoria.name) })
                            }
                        }
                    }
                }
                if (isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )
                }

                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(evento?.imagem)
                            .crossfade(true).build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Imagem do evento ${evento?.nome}"
                    )
                    Text(
                        text = evento?.nome.orEmpty(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = Bold,
                    )
                    Text(
                        text = evento?.descricao.orEmpty().replace("\\n", " \n "),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (openBottomSheet) {
            ModalInscreverEvento(sheetState = sheetState, onSignInClicked = {
                val body = GetPaymentUrlRequestBody(
                    value = 1500,
                    payday = viewModel.getTomorrowDay(),
                    mainPaymentMethodId = "pix",
                    Customer = Customer(myId = "pay-652937390fb2a5.16130871")
                )
                viewModel.getPaymentUrl(body = body)
                openBottomSheet = false
            }) {
                openBottomSheet = false
            }
        }
    }

    if (showDialog) {
        AlertDialog(title = {
            Text(text = "Prosseguir em seu navegador")
        }, text = {
            Text(text = "Ao clicar em prosseguir, você será redirecionado para seu navegador padrão, onde poderá finalizar o pagamento.")
        }, dismissButton = {
            Button(onClick = { showDialog = false }) {
                Text(text = "Cancelar")
            }
        }, onDismissRequest = { showDialog = false }, confirmButton = {
            Button(onClick = {
                uriHandler.openUri(paymentUrl)
            }) {
                Text(text = "Prosseguir")
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalInscreverEvento(
    sheetState: SheetState,
    categorias: List<String> = emptyList(),
    onSignInClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    ModalBottomSheet(sheetState = sheetState, onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            ), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append("Para prosseguir com a inscrição, é necessário concordar com nossa ")
                }

                pushStringAnnotation(tag = "policy", annotation = "")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("política de privacidade")
                }

                pop()
            }
            var checked by remember { mutableStateOf(false) }
            if (categorias.isNotEmpty()) {
                Text(text = "Selecione as categorias que deseja se inscrever")
                LazyRow {
                    items(items = categorias) {

                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "policy",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        showDialog = true
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = !checked
                    }
                )
                Text(
                    text = "Declaro que li e concordo com a política de privacidade",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSignInClicked() },
                enabled = checked
            ) {
                Text(text = "Gerar url de pagamento")
            }
        }
    }

    if (showDialog) {
        FullScreenDialog(
            onDismissRequest = { showDialog = false },
            title = "Termos de uso",
            body = stringResource(
                R.string.termos_de_uso,
                "Hyper Pong",
                "GalaxPay",
                "Academia Hyper Pong de Tênis de Mesa",
                "seu@email.com"
            )
        )
    }
}

@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    title: String,
    body: String
) {
    val scope = rememberCoroutineScope()
    var animateTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            animateTrigger = true
        }
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            scope.launch {
                animateTrigger = false
                delay(200)
                onDismissRequest()
            }
        }
    ) {
        AnimatedVisibility(
            visible = animateTrigger,
            enter = expandHorizontally(),
            exit = shrinkHorizontally(),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            animateTrigger = false
                            delay(200)
                            onDismissRequest()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close dialog"
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Text(
                    modifier = Modifier
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(all = 24.dp),
                    text = body
                )
            }
        }
    }
}