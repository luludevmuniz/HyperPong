package com.alpaca.hyperpong.domain.use_case.authentication.recarregar_usuario

import com.alpaca.hyperpong.domain.repository.AuthRepository

class RecarregarUsuarioUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.recarregarUsuario()
}