package com.alpaca.hyperpong.domain.use_case.firestore.get_event

import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import com.alpaca.hyperpong.domain.repository.GetEventResponse

class GetEventUseCase(
    private val repository: FirestoreRepository
) {
    operator fun invoke(id: String): GetEventResponse = repository.getEvent(id = id)
}