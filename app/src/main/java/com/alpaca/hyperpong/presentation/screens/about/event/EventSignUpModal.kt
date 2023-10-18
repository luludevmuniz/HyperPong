package com.alpaca.hyperpong.presentation.screens.about.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.presentation.common.FullScreenDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventSignUpModal(
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