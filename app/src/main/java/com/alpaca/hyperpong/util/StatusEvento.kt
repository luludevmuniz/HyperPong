package com.alpaca.hyperpong.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed class StatusEvento {
    @Composable
    abstract fun getTint(): Color

    object FUTURO: StatusEvento() {
        @Composable
        override fun getTint(): Color = MaterialTheme.colorScheme.tertiary
    }
    object ABERTO: StatusEvento() {
        @Composable
        override fun getTint(): Color = MaterialTheme.colorScheme.primary
    }
    object FINALIZADO: StatusEvento() {
        @Composable
        override fun getTint(): Color = MaterialTheme.colorScheme.secondary
    }
    object DESCONHECIDO: StatusEvento() {
        @Composable
        override fun getTint(): Color = Color.Black
    }
}