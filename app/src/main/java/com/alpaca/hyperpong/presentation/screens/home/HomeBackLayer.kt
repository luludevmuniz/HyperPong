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
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
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
import com.alpaca.hyperpong.presentation.common.DropdownFilterButton
import com.alpaca.hyperpong.presentation.common.FilterChipRow
import com.alpaca.hyperpong.util.Constantes.categoriasEventos
import com.alpaca.hyperpong.util.FiltroData
import com.alpaca.hyperpong.util.TipoEvento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBackLayer(
    selectedTab: HomeTab,
    onEventTypeSelected: (TipoEvento) -> Unit,
    onTabSelected: (HomeTab) -> Unit,
    onDateChipClicked: (FiltroData) -> Unit
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
        PrimaryTabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
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
            onDateButtonClicked = { showDatePicker = true },
            onEventTypeSelected = { eventType -> onEventTypeSelected(eventType) },
            onDateChipClicked = { filtro -> onDateChipClicked(filtro) }
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
    onDateButtonClicked: () -> Unit,
    onEventTypeSelected: (TipoEvento) -> Unit,
    onDateChipClicked: (FiltroData) -> Unit
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
        var modalidades = emptyList<String>()
        var categorias: List<String>? = null
        when (selectedTab) {
            HomeTab.Eventos -> {
                modalidades = TipoEvento.entries.map { it.toString() }
                categorias = categoriasEventos
            }

            HomeTab.Aulas -> {
                modalidades = emptyList()
            }
        }
        HomeFilterButtons(
            modalidades = modalidades,
            categorias = categorias,
            onDateButtonClicked = { onDateButtonClicked() },
            onEventTypeSelected = { eventType -> onEventTypeSelected(eventType) }
        )
        FilterChipRow(
            items = FiltroData.entries.map { it.name },
            onChipClicked = { filtro -> onDateChipClicked(FiltroData.valueOf(filtro)) }
        )
    }
}

@Composable
private fun HomeFilterButtons(
    modalidades: List<String>,
    categorias: List<String>? = null,
    onDateButtonClicked: () -> Unit,
    onEventTypeSelected: (TipoEvento) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        DropdownFilterButton(
            modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
            title = "Escolher Modalidades",
            leadingIcon = painterResource(id = R.drawable.ic_joystick),
            items = modalidades,
            onItemSelected = { eventType -> onEventTypeSelected(TipoEvento.toEnum(eventType = eventType)) }
        )
        categorias?.let {
            DropdownFilterButton(
                modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
                title = "Selecionar Categorias",
                leadingIcon = painterResource(id = R.drawable.ic_settings_slow_motion),
                items = it,
                onItemSelected = {}
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