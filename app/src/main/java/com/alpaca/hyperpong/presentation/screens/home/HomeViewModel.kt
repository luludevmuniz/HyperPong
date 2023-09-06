package com.alpaca.hyperpong.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.domain.use_case.realtime.RealtimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val realtimeUseCases: RealtimeUseCases
) : ViewModel() {
    val eventos get() = realtimeUseCases.getEventosUseCase(viewModelScope = viewModelScope)

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
