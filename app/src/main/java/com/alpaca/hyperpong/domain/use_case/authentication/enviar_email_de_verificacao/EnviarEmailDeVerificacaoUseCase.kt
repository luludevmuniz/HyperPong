package com.alpaca.hyperpong.domain.use_case.authentication.enviar_email_de_verificacao

import com.alpaca.hyperpong.domain.repository.AuthRepository

class EnviarEmailDeVerificacaoUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.enviarEmailDeVerificacao()
}