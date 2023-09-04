package com.alpaca.hyperpong.domain.use_case.authentication.registrar_usuario_com_email_e_senha

import com.alpaca.hyperpong.domain.repository.AuthRepository

class RegistrarUsuarioComEmailESenhaUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, senha: String) =
        repository.registrarUsuarioComEmailESenha(email = email, senha = senha)
}