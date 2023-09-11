package com.alpaca.hyperpong.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class StatusEvento {
    FUTURO {
        @Composable
        override fun getColor(): Color = MaterialTheme.colorScheme.tertiary
    },
    ABERTO {
        @Composable
        override fun getColor(): Color = MaterialTheme.colorScheme.primary
    },
    FINALIZADO {
        @Composable
        override fun getColor(): Color = MaterialTheme.colorScheme.secondary
    },
    DESCONHECIDO {
        @Composable
        override fun getColor(): Color = if (isSystemInDarkTheme()) Color.White else Color.Black
    };

    @Composable
    abstract fun getColor(): Color
}