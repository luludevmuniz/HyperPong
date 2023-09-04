package com.alpaca.hyperpong.domain.use_case.authentication.enviar_email_para_redefinir_senha

import com.alpaca.hyperpong.domain.repository.AuthRepository

class EnviarEmailParaRedefinirSenhaUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) = repository.enviarEmailParaRedefinirSenha(email = email)
}