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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.presentation.common.TopBarPadrao
import com.alpaca.hyperpong.util.Response

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
    val eventosState by homeViewModel.eventos.collectAsStateWithLifecycle()
    var todosEventos = emptyList<Evento>()
    var eventosConcluidos = emptyList<Evento>()
    var eventosFuturos = emptyList<Evento>()

    LaunchedEffect(eventosState) {
        when (val eventos = eventosState) {
            is Response.Success -> {
                todosEventos = eventos.data
                eventosConcluidos = homeViewModel.filtrarEventosConcluidos(eventos = todosEventos)
                eventosFuturos = homeViewModel.filtrarProximosEventos(eventos = todosEventos)
            }
            is Response.Loading -> {
            }
            is Response.Error -> {
            }
            is Response.Idle -> {
            }
        }
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
                eventos = todosEventos,
                selectedTab = selectedTab,
                onTabSelected = { newSelectedTab ->
                    selectedTab = newSelectedTab
                }
            )
        },
        frontLayerContent = {
            when (selectedTab) {
                HomeTab.Eventos -> HomeFrontLayer(
                    eventosConcluidos = eventosConcluidos,
                    eventosFuturos = eventosFuturos,
                    categoria = selectedTab
                ) { idEvento ->
                    onEventClicked(idEvento)
                }

                HomeTab.Aulas -> HomeFrontLayer(aulas = aulas, categoria = selectedTab) {
                    onAulaClicked()
                }
            }
        }
    )
}