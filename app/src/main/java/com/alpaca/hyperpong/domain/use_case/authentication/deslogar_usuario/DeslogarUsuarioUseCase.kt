package com.alpaca.hyperpong.domain.use_case.authentication.deslogar_usuario

import com.alpaca.hyperpong.domain.repository.AuthRepository

class DeslogarUsuarioUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.deslogarUsuario()
}