package com.alpaca.hyperpong.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.domain.use_case.realtime.RealtimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val realtimeUseCases: RealtimeUseCases
) : ViewModel() {
    val eventos get() = realtimeUseCases.getEventosUseCase(viewModelScope = viewModelScope).cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val eventosConcluidosFlow: Flow<List<Evento>> = eventos
        .flatMapConcat { pagingData ->
            flow {
                val eventosList = mutableListOf<Evento>()
                pagingData.map { evento ->
                    val dataAPI = LocalDate.parse(evento.dataInicio, DateTimeFormatter.ISO_DATE)
                    if (dataAPI.isBefore(LocalDate.now()) || dataAPI.isEqual(LocalDate.now())) {
                        eventosList.add(evento)
                    }
                }
                emit(eventosList)
            }
        }
    fun filtrarEventosConcluidos(eventos: List<Evento>): List<Evento> =
        eventos.filter { evento ->
            val dataAPI = LocalDate.parse(evento.dataInicio, DateTimeFormatter.ISO_DATE)
            dataAPI.isBefore(LocalDate.now()) || dataAPI.isEqual(LocalDate.now())
        }


    fun filtrarProximosEventos(eventos: List<Evento>): List<Evento> = eventos.filter { evento ->
        val dataAPI = LocalDate.parse(evento.dataInicio, DateTimeFormatter.ISO_DATE)
        dataAPI.isAfter(LocalDate.now())
    }
}
