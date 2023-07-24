package com.alpaca.hyperpong.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            title = "Escolher Modalidades",
            leadingIcon = painterResource(id = R.drawable.ic_joystick),
            listItens = modalidades
        )
        niveis?.let {
            DropdownFilterButton(
                title = "Ajustar Nível Recomendado",
                leadingIcon = painterResource(id = R.drawable.ic_settings_slow_motion),
                listItens = it
            )
        }
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface),
            onClick = {},
            shape = ShapeDefaults.ExtraSmall,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 12.dp
            )
        ) {
            Icon(
                tint = MaterialTheme.colors.onSurface,
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
    Divider(modifier = Modifier.padding(vertical = 8.dp))
}