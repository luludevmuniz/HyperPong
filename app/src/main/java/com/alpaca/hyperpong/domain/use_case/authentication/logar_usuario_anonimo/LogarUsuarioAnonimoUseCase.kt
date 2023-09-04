package com.alpaca.hyperpong.domain.use_case.authentication.logar_usuario_anonimo

import com.alpaca.hyperpong.domain.repository.AuthRepository

class LogarUsuarioAnonimoUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.logarUsuarioAnonimo()
}