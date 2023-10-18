package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.presentation.common.ErrorItem
import com.alpaca.hyperpong.util.FiltroData
import com.alpaca.hyperpong.util.FiltroData.Concluidos
import com.alpaca.hyperpong.util.FiltroData.Futuros

@Composable
fun HomeFrontLayer(
    modifier: Modifier = Modifier,
    categoria: HomeTab,
    loadState: CombinedLoadStates,
    proximosEvents: List<Event>,
    eventosConcluidos: List<Event>,
    dateFilters: List<FiltroData> = emptyList(),
    onRetryClicked: () -> Unit,
    onItemClicked: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 24.dp)
    ) {
        LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
            if (dateFilters.contains(Futuros) && proximosEvents.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(
                            top = 24.dp,
                            bottom = 16.dp
                        ),
                        text = "Próximos Eventos",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 16.sp
                    )
                }
                items(items = proximosEvents) { evento ->
                    EventoItem(event = evento, onItemClicked = { idEvento ->
                        onItemClicked(idEvento)
                    })
                }
            }

            if (dateFilters.contains(Concluidos) && eventosConcluidos.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(
                            top = 24.dp,
                            bottom = 16.dp
                        ),
                        text = "Eventos Concluídos",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 16.sp
                    )
                }

                items(items = eventosConcluidos) { evento ->
                    EventoItem(event = evento) { idEvento ->
                        onItemClicked(idEvento)
                    }
                }
            }
        }

        when (loadState.prepend) {
            is LoadState.Loading -> {
                repeat(2) {
                    ShimmerEffect(modifier = Modifier.padding(bottom = 8.dp))
                }
            }

            is LoadState.Error -> {
                ErrorItem {
                    onRetryClicked()
                }
            }

            else -> {}
        }

        when (loadState.append) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                ErrorItem {
                    onRetryClicked()
                }
            }

            else -> {}
        }

        when (loadState.refresh) {
            is LoadState.Loading -> {
                repeat(2) {
                    ShimmerEffect(modifier = Modifier.padding(bottom = 8.dp))
                }
            }

            is LoadState.Error -> {
                ErrorItem {
                    onRetryClicked()
                }
            }

            else -> {}
        }
    }
}