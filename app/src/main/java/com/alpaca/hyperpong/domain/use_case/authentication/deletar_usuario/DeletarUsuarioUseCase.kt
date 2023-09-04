package com.alpaca.hyperpong.domain.use_case.authentication.deletar_usuario

import com.alpaca.hyperpong.domain.repository.AuthRepository
import com.alpaca.hyperpong.domain.repository.RetornoDeletarUsuario

class DeletarUsuarioUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.deletarUsuario()
}