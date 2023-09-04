package com.alpaca.hyperpong.presentation.screens.about.aulas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.presentation.common.SessaoAula
import com.alpaca.hyperpong.ui.theme.HyperPongTheme

@Composable
fun AboutAulasContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SessaoAula(
            tituloSessao = "Aula particular",
            valorAvulso = "50 reais",
            valorMensal = "160 reais",
            frequenciaAvulsa = "1 hora",
            frequenciaMensal = "4 horas"
        ) {}
        HorizontalDivider()
        SessaoAula(
            tituloSessao = "Aula em dupla",
            valorAvulso = "70 reais",
            valorMensal = "200 reais",
            frequenciaAvulsa = "1 hora",
            frequenciaMensal = "4 horas"
        ) {}
        HorizontalDivider()
        SessaoAula(
            tituloSessao = "Aula coletiva",
            valorAvulso = "30 reais",
            valorMensal = "170 reais",
            frequenciaAvulsa = "1h30min",
            frequenciaMensal = "2 semanalmente"
        ) {}
    }
}

@Preview(showSystemUi = true)
@Composable
fun AboutAulasContentPrev() {
    HyperPongTheme {
        AboutAulasContent()
    }
}