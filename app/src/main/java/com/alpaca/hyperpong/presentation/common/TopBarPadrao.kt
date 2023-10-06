package com.alpaca.hyperpong.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPadrao(titulo: String, onNavigationIconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = titulo) },
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                Icon(
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    imageVector = Icons.Default.Person,
                    contentDescription = "Menu Icon"
                )
            }
        }
    )
}