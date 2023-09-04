package com.alpaca.hyperpong.domain.use_case.authentication.get_auth_state

import com.alpaca.hyperpong.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope

class GetAuthStateUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(viewModelScope: CoroutineScope) =
        repository.getAuthState(viewModelScope = viewModelScope)
}