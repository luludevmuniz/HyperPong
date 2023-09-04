package com.alpaca.hyperpong.domain.use_case.authentication.is_email_verificado

import com.alpaca.hyperpong.domain.repository.AuthRepository

class IsEmailVerificadoUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.isEmailVerificado()
}