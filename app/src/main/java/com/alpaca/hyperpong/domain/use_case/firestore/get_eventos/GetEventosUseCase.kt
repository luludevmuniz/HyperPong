package com.alpaca.hyperpong.domain.use_case.firestore.get_eventos

import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import com.alpaca.hyperpong.domain.repository.RetornoEventos

class GetEventosUseCase(
    private val repository: FirestoreRepository
) {
    operator fun invoke(): RetornoEventos = repository.getEvents()
}