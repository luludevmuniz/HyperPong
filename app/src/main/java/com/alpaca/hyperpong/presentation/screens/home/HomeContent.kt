package com.alpaca.hyperpong.presentation.screens.home

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropScaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import com.alpaca.hyperpong.util.FiltroData
import com.alpaca.hyperpong.util.TipoEvento
import okhttp3.internal.filterList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigationIconClicked: () -> Unit,
    onEventClicked: (String) -> Unit,
    onAulaClicked: () -> Unit
) {
    var selectedTab by remember {
        mutableStateOf(HomeTab.Eventos)
    }
    var selectedEventTypes by remember {
        mutableStateOf(listOf<TipoEvento>())
    }
    var dateFilters by remember {
        mutableStateOf(listOf<FiltroData>())
    }

    BackdropScaffold(
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        appBar = {
            TopBarPadrao(titulo = "Hyper Pong") {
                onNavigationIconClicked()
            }
        },
        frontLayerScrimColor = Color.Unspecified,
        backLayerBackgroundColor = MaterialTheme.colorScheme.surface,
        backLayerContent = {
            HomeBackLayer(
                selectedTab = selectedTab,
                onEventTypeSelected = { eventType ->
                    selectedEventTypes = if (selectedEventTypes.contains(eventType)) {
                        selectedEventTypes.filterNot {
                            it == eventType
                        }
                    } else {
                        selectedEventTypes.plus(eventType)
                    }
                },
                onTabSelected = { newSelectedTab ->
                    selectedTab = newSelectedTab
                },
                onDateChipClicked = { dateChipSelected ->
                    dateFilters = if (dateFilters.contains(dateChipSelected)) {
                        dateFilters.filterNot {
                            it == dateChipSelected
                        }
                    } else {
                        dateFilters.plus(dateChipSelected)
                    }
                }
            )
        },
        frontLayerContent = {
            val eventos = homeViewModel.eventos.collectAsLazyPagingItems()
            var proximosEventos by remember { mutableStateOf(emptyList<Evento>()) }
            var eventosConcluidos by remember { mutableStateOf(emptyList<Evento>()) }

            LaunchedEffect(
                eventos.itemCount,
                selectedEventTypes
            ) {
                eventosConcluidos = eventos.itemSnapshotList.filterList {
                    this?.let {
                        selectedEventTypes
                            .ifEmpty { TipoEvento.entries.toList() }
                            .contains(it.tipoEvento) && it.isConcluido()
                    } ?: false
                }.filterNotNull().sortedBy { evento ->
                    evento.dataInicioFormatada
                }

                proximosEventos = eventos.itemSnapshotList.filterList {
                    this?.let {
                        selectedEventTypes
                            .ifEmpty { TipoEvento.entries.toList() }
                            .contains(it.tipoEvento) && it.isFuturo()
                    } ?: false
                }.filterNotNull().sortedBy { evento ->
                    evento.dataInicioFormatada
                }
            }

            when (selectedTab) {
                HomeTab.Eventos -> HomeFrontLayer(
                    categoria = selectedTab,
                    loadState = eventos.loadState,
                    eventosConcluidos = eventosConcluidos,
                    proximosEventos = proximosEventos,
                    dateFilters = dateFilters.ifEmpty { FiltroData.entries.toList() },
                    onRetryClicked = { eventos.retry() }
                ) { idEvento ->
                    onEventClicked(idEvento)
                }

                HomeTab.Aulas -> HomeFrontLayer(
                    categoria = selectedTab,
                    loadState = eventos.loadState,
                    eventosConcluidos = eventosConcluidos,
                    proximosEventos = proximosEventos,
                    onRetryClicked = { eventos.retry() }
                ) {
                    onAulaClicked()
                }
            }
        }
    )
}