package com.alpaca.hyperpong.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R

@Composable
fun HomeFilterButtons(
    modalidades: List<String>,
    niveis: List<String>? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        DropdownFilterButton(
            modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
            title = "Escolher Modalidades",
            leadingIcon = painterResource(id = R.drawable.ic_joystick),
            listItens = modalidades
        )
        niveis?.let {
            DropdownFilterButton(
                modifier = Modifier.clip(shape = MaterialTheme.shapes.small),
                title = "Ajustar Nível Recomendado",
                leadingIcon = painterResource(id = R.drawable.ic_settings_slow_motion),
                listItens = it
            )
        }
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.surface),
            onClick = {},
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 12.dp
            )
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Ícone Joystick"
            )
            Spacer(modifier = Modifier.size(width = 12.dp, height = 0.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "Selecionar Datas"
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}