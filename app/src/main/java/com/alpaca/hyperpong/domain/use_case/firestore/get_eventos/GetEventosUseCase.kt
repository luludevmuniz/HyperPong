package com.alpaca.hyperpong.domain.use_case.firestore.get_eventos

import androidx.paging.PagingData
import com.alpaca.hyperpong.domain.model.firestore.Event
import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow

typealias RetornoEventos = Flow<PagingData<Event>>
class GetEventosUseCase(
    private val repository: FirestoreRepository
) {
    operator fun invoke(): RetornoEventos = repository.getEvents()
}