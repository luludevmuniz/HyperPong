package com.alpaca.hyperpong.presentation.screens.about.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.presentation.common.DescricaoEvento
import com.alpaca.hyperpong.ui.theme.HyperPongTheme

@Composable
fun AboutEventsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        DescricaoEvento(
            modifier = Modifier.padding(12.dp),
            titulo = "Copa Hyper",
            descricao = stringResource(id = R.string.lorem_ipsum),
            valor = "50",
            apenasAlunos = false,
            valeRating = true,
            periodoOcorrencia = "A cada 3 meses"
        )
        DescricaoEvento(
            modifier = Modifier.padding(12.dp),
            titulo = "Torneio Interno",
            descricao = stringResource(id = R.string.lorem_ipsum),
            valor = "15",
            apenasAlunos = true,
            valeRating = true,
            periodoOcorrencia = "Mensal"
        )
        DescricaoEvento(
            modifier = Modifier.padding(12.dp),
            titulo = "Rachão",
            descricao = stringResource(id = R.string.lorem_ipsum),
            valor = "20",
            apenasAlunos = false,
            valeRating = true,
            periodoOcorrencia = "Última sexta-feira do mês"
        )
        DescricaoEvento(
            modifier = Modifier.padding(12.dp),
            titulo = "Bate bola",
            descricao = stringResource(id = R.string.lorem_ipsum),
            valor = "0",
            apenasAlunos = false,
            valeRating = false,
            periodoOcorrencia = "Sexta-feira, sábado e feriados"
        )
    }
}

@Preview
@Composable
fun AboutEventsScreenPrev() {
    HyperPongTheme {
        AboutEventsContent()
    }
}