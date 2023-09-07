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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.presentation.common.TopBarPadrao

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    onNavigationIconClicked: () -> Unit,
    onEventClicked: (String) -> Unit,
    onAulaClicked: () -> Unit
) {
    var selectedTab by remember {
        mutableStateOf(HomeTab.Eventos)
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
                onTabSelected = { newSelectedTab ->
                    selectedTab = newSelectedTab
                }
            )
        },
        frontLayerContent = {
            when (selectedTab) {
                HomeTab.Eventos -> HomeFrontLayer(
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