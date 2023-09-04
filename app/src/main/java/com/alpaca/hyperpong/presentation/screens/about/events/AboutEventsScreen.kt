package com.alpaca.hyperpong.presentation.screens.about.events

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alpaca.hyperpong.presentation.common.TopBarPadrao

@Composable
fun AboutEventsScreen(onNavigationIconClicked: () -> Unit) {
    Scaffold(topBar = {
        TopBarPadrao(titulo = "Sobre os eventos") {
            onNavigationIconClicked()
        }
    }) { innerPadding ->
        AboutEventsContent(modifier = Modifier.padding(innerPadding))
    }
}