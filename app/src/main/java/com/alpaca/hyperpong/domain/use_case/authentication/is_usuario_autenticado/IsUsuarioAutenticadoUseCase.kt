package com.alpaca.hyperpong.domain.use_case.authentication.is_usuario_autenticado

import com.alpaca.hyperpong.domain.repository.AuthRepository

class IsUsuarioAutenticadoUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.isUsuarioAutenticado()
}