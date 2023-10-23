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
import com.alpaca.hyperpong.domain.model.firestore.Event
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import com.alpaca.hyperpong.presentation.screens.home.back_layer.HomeBackLayer
import com.alpaca.hyperpong.presentation.screens.home.front_layer.HomeFrontLayer
import com.alpaca.hyperpong.util.enums.FiltroData
import com.alpaca.hyperpong.util.enums.TipoCategoria
import com.alpaca.hyperpong.util.enums.TipoEvento
import com.alpaca.hyperpong.util.isError
import com.alpaca.hyperpong.util.isLoading
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
    var selectedCategoryTypes by remember {
        mutableStateOf(listOf<TipoCategoria>())
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
                onCategoryTypeSelected = { categoryType ->
                    selectedCategoryTypes = if (selectedCategoryTypes.contains(categoryType)) {
                        selectedCategoryTypes.filterNot {
                            it == categoryType
                        }
                    } else {
                        selectedCategoryTypes.plus(categoryType)
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
            var proximosEvents by remember { mutableStateOf(emptyList<Event>()) }
            var eventosConcluidos by remember { mutableStateOf(emptyList<Event>()) }

            LaunchedEffect(
                eventos.itemCount,
                selectedEventTypes,
                selectedCategoryTypes
            ) {
                eventosConcluidos = eventos.itemSnapshotList.filterList {
                    this?.let {
                        selectedEventTypes
                            .ifEmpty { TipoEvento.entries.toList() }
                            .contains(it.tipoEvento) && it.isConcluido()
                                &&
                        categories.any { category ->
                            selectedCategoryTypes
                                .ifEmpty { TipoCategoria.entries.toList() }
                                .contains(category.tipoCategoria)
                        }
                    } ?: false
                }.filterNotNull().sortedBy { evento ->
                    evento.dataInicioFormatada
                }

                proximosEvents = eventos.itemSnapshotList.filterList {
                    this?.let {
                        selectedEventTypes
                            .ifEmpty { TipoEvento.entries.toList() }
                            .contains(it.tipoEvento) && it.isFuturo()
                                &&
                        categories.any { category ->
                            selectedCategoryTypes
                                .ifEmpty { TipoCategoria.entries.toList() }
                                .contains(category.tipoCategoria)
                        }
                    } ?: false
                }.filterNotNull().sortedBy { evento ->
                    evento.dataInicioFormatada
                }
            }

            when (selectedTab) {
                HomeTab.Eventos -> HomeFrontLayer(
                    isLoading = eventos.loadState.isLoading,
                    isError = eventos.loadState.isError,
                    completedEvents = eventosConcluidos,
                    nextEvents = proximosEvents,
                    dateFilters = dateFilters.ifEmpty { FiltroData.entries.toList() },
                    onRetryClicked = { eventos.retry() }
                ) { idEvento ->
                    onEventClicked(idEvento)
                }

                HomeTab.Aulas -> HomeFrontLayer(
                    isLoading = eventos.loadState.isLoading,
                    isError = eventos.loadState.isError,
                    completedEvents = eventosConcluidos,
                    nextEvents = proximosEvents,
                    onRetryClicked = { eventos.retry() }
                ) {
                    onAulaClicked()
                }
            }
        }
    )
}