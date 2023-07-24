package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.presentation.common.FilterChipRow
import com.alpaca.hyperpong.presentation.common.HomeFilterButtons

@Composable
fun HomeBackLayer(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    Column(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.surface)
        .verticalScroll(state = rememberScrollState())) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            HomeTab.values().forEach { tab ->
                Tab(
                    selected = tab == selectedTab,
                    onClick = { onTabSelected(tab) },
                    text = { Text(text = tab.name) }
                )
            }
        }
        HomeBackLayerContent(selectedTab)
    }
}

@Composable
private fun HomeBackLayerContent(selectedTab: HomeTab) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 32.dp
            ),
    ) {
        var listaModalidades = emptyList<String>()
        var listaNiveis: List<String>? = null
        when (selectedTab) {
            HomeTab.Eventos -> {
                listaModalidades = listOf("Copa Hyper", "Rachão", "Torneio Interno", "Bate Bola")
                listaNiveis = listOf("Iniciante", "Intermediário", "Avançado")
            }

            HomeTab.Aulas -> {
                listaModalidades =
                    listOf("Aula Individual", "Aula Em Dupla", "Aula Coletiva", "Treino Secreto")
            }
        }
        HomeFilterButtons(modalidades = listaModalidades, niveis = listaNiveis)
        FilterChipRow(items = listOf("Futuros", "Concluídos"))
    }
}