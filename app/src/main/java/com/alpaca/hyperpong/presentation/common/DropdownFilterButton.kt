package com.alpaca.hyperpong.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFilterButton(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: Painter,
    trailingIcon: Painter? = painterResource(id = R.drawable.ic_play_arrow),
    items: List<String>,
    enabled: Boolean = false,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val trailingIconRotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f,
        label = "Trailing Icon Rotation Anim"
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = !expanded }
    ) {
        FilledTonalButton(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
                .menuAnchor(),
            enabled = false,
            onClick = {
            },
            shape = ShapeDefaults.ExtraSmall,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 12.dp
            )
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = leadingIcon,
                contentDescription = "Ícone Joystick"
            )
            Spacer(modifier = Modifier.size(width = 12.dp, height = 0.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = title
            )
            trailingIcon?.let {
                Icon(
                    modifier = Modifier.rotate(trailingIconRotation),
                    tint = MaterialTheme.colorScheme.onSurface,
                    painter = trailingIcon,
                    contentDescription = "Ícone Joystick"
                )
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    var menuItemSelected by remember { mutableStateOf(false) }
                    val menuItemTrailingIcon = if (menuItemSelected) Icons.Default.Check
                    else Icons.Default.Add

                    DropdownMenuItem(
                        text = { Text(text = item) },
                        trailingIcon = {
                            Icon(
                                imageVector = menuItemTrailingIcon,
                                contentDescription = "Select Event Icon",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            menuItemSelected = !menuItemSelected
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}