package com.alpaca.hyperpong.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipRow(
    modifier: Modifier = Modifier,
    items: List<String>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        var selectedChips by remember { mutableStateOf(setOf<String>()) }
        items.forEach { item ->
            FilterChip(
                selected = item in selectedChips,
                onClick = {
                    selectedChips = if (item in selectedChips) {
                        selectedChips - item
                    } else {
                        selectedChips + item
                    }
                },
                label = {
                    Text(text = item)
                },
                leadingIcon = {
                    AnimatedVisibility(visible = item in selectedChips) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "$item Filter Chip"
                        )
                    }
                }
            )
        }
    }
}