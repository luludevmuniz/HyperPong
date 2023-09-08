package com.alpaca.hyperpong.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltipGenerico(
    modifier: Modifier = Modifier,
    contentAligment: Alignment = Alignment.CenterEnd,
    titulo: String,
    descricao: String,
    textoAcao: String,
    onActionClicked: () -> Unit
) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = contentAligment
    ) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                RichTooltip(
                    action = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    tooltipState.dismiss()
                                    onActionClicked()
                                }
                            }
                        ) { Text(textoAcao) }
                    },
                    text = {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bolt_sharp),
                                contentDescription = null
                            )
                            Text(descricao)
                        }
                    }
                )
            },
            state = tooltipState
        ) {
            OutlinedIconButton(
                modifier = Modifier.size(size = 20.dp),
                border = BorderStroke(
                    width = Dp.Hairline,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                onClick = { }
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                    painter = painterResource(id = R.drawable.ic_question_mark),
                    contentDescription = "Tooltip Icon"
                )
            }
        }
    }
}