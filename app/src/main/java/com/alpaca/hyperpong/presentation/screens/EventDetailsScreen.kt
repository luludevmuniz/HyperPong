package com.alpaca.hyperpong.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.presentation.common.ItemIconeTexto
import com.alpaca.hyperpong.ui.theme.HyperPongTheme

@Composable
fun EventDetailsScreen(
    onNavigationIconClicked: () -> Unit
) {
    Scaffold(
        topBar = {
                 //TODO: Implementar toolbar fundo degrade e apenas ícone hamburguer
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
                onClick = { /*TODO*/ })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.foto_hyper),
                contentDescription = "Event Image"
            )
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
                        top = 24.dp,
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 16.dp
                    )
            ) {
                Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ItemIconeTexto(
                            icone = painterResource(id = R.drawable.ic_money_value),
                            texto = "Valor: R$ 50,00"
                        )
                        ItemIconeTexto(
                            icone = painterResource(id = R.drawable.ic_subscription_open),
                            texto = "Participantes: 9/16"
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ItemIconeTexto(
                            icone = painterResource(id = R.drawable.ic_event_day),
                            texto = "Data: 24/08/2023"
                        )
                        ItemIconeTexto(
                            icone = painterResource(id = R.drawable.ic_alarm),
                            texto = "Horário: 19h às 22h"
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
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
                            label = { Text(text = "Iniciante") }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
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
                            label = { Text(text = "Intermediário") }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "3a edição da Copa Hyper",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = Bold,
                )
                Text(
                    text = "Lorem ipsum dolor sit amet consectetur. At consectetur netus dignissim vitae morbi. Non dictumst justo non enim interdum sit. Diam mauris velit viverra sed viverra ut purus. Nulla sit id amet dolor auctor.Lorem ipsum dolor sit amet consectetur. At consectetur netus dignissim vitae morbi. Non dictumst justo non enim interdum sit. Diam mauris velit viverra sed viverra ut purus. Nulla sit id amet dolor auctor.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EventDetailsScreenPrev() {
    HyperPongTheme {
        EventDetailsScreen {}
    }
}