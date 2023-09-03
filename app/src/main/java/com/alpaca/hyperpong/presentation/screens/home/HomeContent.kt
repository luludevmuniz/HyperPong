package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    onNavigationIconClicked: () -> Unit,
    onEventClicked: () -> Unit,
    onAulaClicked: () -> Unit
) {
    var selectedTab by remember {
        mutableStateOf(HomeTab.Eventos)
    }
    BackdropScaffold(
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        appBar = {
            HomeTopBar {
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
                HomeTab.Eventos -> HomeFrontLayer(eventos = eventos, categoria = selectedTab) {
                    onEventClicked()
                }

                HomeTab.Aulas -> HomeFrontLayer(aulas = aulas, categoria = selectedTab) {
                    onAulaClicked()
                }
            }
        }
    )
}