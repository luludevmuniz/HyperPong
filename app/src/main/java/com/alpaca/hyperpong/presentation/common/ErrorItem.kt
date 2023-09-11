package com.alpaca.hyperpong.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Icon(
                tint = MaterialTheme.colorScheme.onErrorContainer,
                imageVector = Icons.Default.Warning,
                contentDescription = null
            )
        }
        Text(
            text = "Ocorreu um erro",
            color = MaterialTheme.colorScheme.onErrorContainer
        )
        OutlinedIconButton(
            modifier = Modifier.size(size = 32.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onErrorContainer
            ),
            onClick = { onRetryClicked() }
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onErrorContainer,
                imageVector = Icons.Default.Refresh,
                contentDescription = "Recarregar itens"
            )
        }
    }
}