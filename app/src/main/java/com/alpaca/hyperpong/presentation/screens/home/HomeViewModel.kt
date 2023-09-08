package com.alpaca.hyperpong.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.domain.use_case.realtime.RealtimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    realtimeUseCases: RealtimeUseCases
) : ViewModel() {
     val _eventos = realtimeUseCases.getEventosUseCase().cachedIn(viewModelScope)

    val eventosConcluidos: Flow<PagingData<Evento>> = _eventos.map { pagingData ->
        pagingData.filter { evento ->
            evento.isConcluido()
        }
    }
    val proximosEventos: Flow<PagingData<Evento>> = _eventos.map { pagingData ->
        pagingData.filter { evento ->
            evento.isFuturo()
        }
    }
}
