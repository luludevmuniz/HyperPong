package com.alpaca.hyperpong.domain.use_case.firestore.get_user

import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import com.alpaca.hyperpong.domain.repository.GetUserResponse

class GetUserUseCase(
    private val repository: FirestoreRepository
) {
    operator fun invoke(): GetUserResponse = repository.getUser()
}