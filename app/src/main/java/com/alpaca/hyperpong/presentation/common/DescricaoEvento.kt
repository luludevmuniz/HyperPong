package com.alpaca.hyperpong.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.ui.theme.HyperPongTheme

@Composable
fun DescricaoEvento(
    modifier: Modifier = Modifier,
    titulo: String,
    valor: String,
    apenasAlunos: Boolean,
    valeRating: Boolean,
    periodoOcorrencia: String,
    descricao: String,
) {
    var isDetailsVisibile by remember {
        mutableStateOf(false)
    }
    val textoBotao by remember {
        derivedStateOf {
            if (isDetailsVisibile) "Ocultar detalhes" else "Ver detalhes"
        }
    }

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10))
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bolt_sharp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiaryContainer)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            TextButton(onClick = { isDetailsVisibile = !isDetailsVisibile }) {
                Text(text = textoBotao, color = MaterialTheme.colorScheme.tertiary)
            }
        }

        AnimatedVisibility(visible = isDetailsVisibile) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ItemIconeTexto(
                    modifier = Modifier.fillMaxWidth(),
                    icone = painterResource(id = R.drawable.ic_money_value),
                    texto = "Preço: $valor reais"
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Evento apenas para alunos?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    Text(
                        text = if (apenasAlunos) "Sim" else "Não",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Vale pontos de rating interno?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = if (valeRating) "Sim" else "Não",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Período de ocorrência:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = periodoOcorrencia,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        Text(
            text = descricao,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DescricaoEventoPrev() {
    HyperPongTheme {
        DescricaoEvento(
            modifier = Modifier.padding(24.dp).wrapContentHeight(),
            titulo = "Copa Hyper",
            descricao = stringResource(id = R.string.lorem_ipsum),
            valor = "50",
            apenasAlunos = false,
            valeRating = true, 
            periodoOcorrencia = "A cada 3 meses"
        )
    }
}