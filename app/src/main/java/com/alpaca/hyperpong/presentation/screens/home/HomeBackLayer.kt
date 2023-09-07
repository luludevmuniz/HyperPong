package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.presentation.common.DropdownFilterButton
import com.alpaca.hyperpong.presentation.common.FilterChipRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBackLayer(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(state = rememberScrollState())
    ) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            HomeTab.entries.forEach { tab ->
                Tab(
                    selected = tab == selectedTab,
                    onClick = { onTabSelected(tab) },
                    text = { Text(text = tab.name, color = MaterialTheme.colorScheme.onSurface) }
                )
            }
        }
        HomeBackLayerContent(
            selectedTab = selectedTab,
            onDateButtonClicked = { showDatePicker = true }
        )
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = { }) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun HomeBackLayerContent(
    selectedTab: HomeTab,
    onDateButtonClicked: () -> Unit
) {
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
        HomeFilterButtons(
            modalidades = listaModalidades,
            categorias = listaNiveis,
            onDateButtonClicked = { onDateButtonClicked() }
        )
        FilterChipRow(items = listOf("Futuros", "Concluídos"))
    }
}

@Composable
private fun HomeFilterButtons(
    modalidades: List<String>,
    categorias: List<String>? = null,
    onDateButtonClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        DropdownFilterButton(
            modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
            title = "Escolher Modalidades",
            leadingIcon = painterResource(id = R.drawable.ic_joystick),
            listItens = modalidades
        )
        categorias?.let {
            DropdownFilterButton(
                modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
                title = "Selecionar Categorias",
                leadingIcon = painterResource(id = R.drawable.ic_settings_slow_motion),
                listItens = it
            )
        }
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.surface),
            onClick = { onDateButtonClicked() },
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 12.dp
            )
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Ícone Joystick"
            )
            Spacer(modifier = Modifier.size(width = 12.dp, height = 0.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "Selecionar Datas"
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}