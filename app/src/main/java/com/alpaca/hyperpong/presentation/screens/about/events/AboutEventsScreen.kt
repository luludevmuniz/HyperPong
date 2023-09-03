package com.alpaca.hyperpong.presentation.screens.about.events

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alpaca.hyperpong.presentation.screens.home.HomeTopBar

@Composable
fun AboutEventsScreen(onNavigationIconClicked: () -> Unit) {
    Scaffold(
        topBar = {
            HomeTopBar {
                onNavigationIconClicked()
            }
        }
    ) { innerPadding ->
        AboutEventsContent(modifier = Modifier.padding(innerPadding))
    }
}