package com.alpaca.hyperpong.presentation.screens.detalhes_evento

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.presentation.common.ItemIconeTexto
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesEventoScreen(
    homeViewModel: DetalhesEventoViewModel = hiltViewModel(),
    eventoId: String,
    onNavigationIconClicked: () -> Unit
) {
    homeViewModel.getEvento(id = eventoId)
    val evento by homeViewModel.evento.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarPadrao(titulo = evento?.nome.orEmpty()) {
                onNavigationIconClicked()
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Inscrever") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit_square),
                        contentDescription = "Se inscrever no evento"
                    )
                },
                onClick = { openBottomSheet = true })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                                ElevatedSuggestionChip(
                                    enabled = false,
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
                                    label = { Text(text = categoria.name) }
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(evento?.imagem)
                            .crossfade(true)
                            .build(),
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
            ModalInscreverEvento(sheetState = sheetState, onCopyPix = { codigoPix ->
                openBottomSheet = false
                clipboardManager.setText(codigoPix)
                scope.launch {
                    snackbarHostState.showSnackbar("Código pix copiado com sucesso!")
                }
            }) {
                openBottomSheet = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalInscreverEvento(
    sheetState: SheetState,
    categorias: List<String> = emptyList(),
    onCopyPix: (AnnotatedString) -> Unit,
    onDismissRequest: () -> Unit
) {
    val codigoPix = "123123123"

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (categorias.size > 1) {
                Text(text = "Selecione as categorias que deseja se inscrever")
                LazyRow {
                    items(items = categorias) {

                    }
                }
            }
            Row {
                Text(text = "Preço: R\$ 20,00")
            }
            Text(text = "Código pix:")
            Text(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onCopyPix(AnnotatedString(codigoPix))
                }
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                .padding(12.dp),
                text = codigoPix,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer)
            TextButton(modifier = Modifier.fillMaxWidth(),
                onClick = { onCopyPix(AnnotatedString(codigoPix)) }) {
                Text(text = "Copiar código")
            }
        }
    }
}