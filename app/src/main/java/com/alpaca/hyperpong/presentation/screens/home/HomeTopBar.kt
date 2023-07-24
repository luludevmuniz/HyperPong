package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onNavigationIconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Hyper Pong") },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable { onNavigationIconClick() },
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Icon"
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}