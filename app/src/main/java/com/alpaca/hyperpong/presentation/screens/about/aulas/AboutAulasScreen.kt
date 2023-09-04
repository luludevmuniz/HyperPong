package com.alpaca.hyperpong.presentation.screens.about.aulas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alpaca.hyperpong.presentation.common.TopBarPadrao

@Composable
fun AboutAulasScreen(onNavigationIconClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopBarPadrao("Sobre as aulas") {
                onNavigationIconClicked()
            }
        }
    ) { innerPadding ->
        AboutAulasContent(modifier = Modifier.padding(innerPadding))
    }
}