package com.alpaca.hyperpong.presentation.screens.home.front_layer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpaca.hyperpong.domain.model.firestore.Event
import com.alpaca.hyperpong.presentation.common.ErrorItem
import com.alpaca.hyperpong.presentation.common.EventoItem
import com.alpaca.hyperpong.presentation.common.ShimmerEffect
import com.alpaca.hyperpong.util.FiltroData
import com.alpaca.hyperpong.util.FiltroData.Concluidos
import com.alpaca.hyperpong.util.FiltroData.Futuros
import kotlinx.coroutines.delay

@Composable
fun HomeFrontLayer(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    nextEvents: List<Event>,
    completedEvents: List<Event>,
    dateFilters: List<FiltroData> = emptyList(),
    onRetryClicked: () -> Unit,
    onItemClicked: (String) -> Unit
) {
    val nextEventsNotEmpty = dateFilters.contains(Futuros) && nextEvents.isNotEmpty()
    val completedEventsNotEmpty = dateFilters.contains(Concluidos) && completedEvents.isNotEmpty()
    val emptyScreenAnimVisibleState = remember { MutableTransitionState(false).apply { targetState = false } }
    val completedEventsAnimVisibleState = remember { MutableTransitionState(false).apply { targetState = true } }

    LaunchedEffect(isLoading, isError, nextEventsNotEmpty, completedEventsNotEmpty) {
        if (!completedEventsNotEmpty) {
            completedEventsAnimVisibleState.targetState = false
        }
        if (isLoading || isError || nextEventsNotEmpty || completedEventsNotEmpty) {
            emptyScreenAnimVisibleState.targetState = false
        } else {
            delay(300)
            emptyScreenAnimVisibleState.targetState = true
        }
        if (completedEventsNotEmpty) {
            delay(300)
            completedEventsAnimVisibleState.targetState = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(all = 24.dp)
    ) {
        AnimatedVisibility(
            visibleState = emptyScreenAnimVisibleState,
            enter = slideInVertically(animationSpec = spring(Spring.DampingRatioHighBouncy)),
            exit = slideOutVertically(animationSpec = tween(durationMillis = 200))

        ) {
            Text(
                text = "Não encontramos nenhum evento para o filtro selecionado.",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        AnimatedVisibility(
            visible = nextEventsNotEmpty,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)),
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 200))
        ) {
            LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
                item {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Próximos Eventos",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 16.sp
                    )
                }
                items(items = nextEvents) { evento ->
                    EventoItem(
                        event = evento,
                        onItemClicked = { idEvento ->
                            onItemClicked(idEvento)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visibleState = completedEventsAnimVisibleState,
            enter = expandHorizontally(animationSpec = tween(durationMillis = 200)),
            exit = shrinkHorizontally(animationSpec = tween(durationMillis = 200))
        ) {
            if (completedEvents.isNotEmpty()) {

                LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
                    item {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = "Eventos Concluídos",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 16.sp
                        )
                    }

                    items(items = completedEvents) { evento ->
                        EventoItem(event = evento) { idEvento ->
                            onItemClicked(idEvento)
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            isLoading,
            enter = slideInVertically(animationSpec = tween(durationMillis = 200)),
            exit = scaleOut(animationSpec = tween(durationMillis = 200))
        ) {
            repeat(2) {
                ShimmerEffect(modifier = Modifier.padding(top = 12.dp))
            }
        }

        AnimatedVisibility(
            isError,
            enter = scaleIn(animationSpec = tween(durationMillis = 200)),
            exit = scaleOut(animationSpec = tween(durationMillis = 200))
        ) {
            ErrorItem {
                onRetryClicked()
            }
        }
    }
}