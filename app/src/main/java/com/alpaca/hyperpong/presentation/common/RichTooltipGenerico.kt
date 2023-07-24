package com.alpaca.hyperpong.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberRichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltipGenerico(
    modifier: Modifier = Modifier,
    titulo: String,
    descricao: String,
    textoAcao: String,
    onActionClicked: () -> Unit
) {
    val tooltipState = rememberRichTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    RichTooltipBox(
        modifier = modifier,
        tooltipState = tooltipState,
        title = { Text(text = titulo) },
        text = {
            Text(text = descricao)
        },
        action = {
            TextButton(
                onClick = {
                    scope.launch {
                        tooltipState.dismiss()
                        onActionClicked()
                    }
                }
            ) { Text(textoAcao) }
        }
    ) {
        OutlinedIconButton(
            modifier = Modifier
                .size(size = 20.dp)
                .tooltipTrigger(),
            border = BorderStroke(
                width = Dp.Hairline,
                color = MaterialTheme.colorScheme.onSurface
            ),
            onClick = { /*TODO*/ }
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