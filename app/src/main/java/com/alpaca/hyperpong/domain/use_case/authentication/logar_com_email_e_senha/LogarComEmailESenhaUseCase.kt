package com.alpaca.hyperpong.domain.use_case.authentication.logar_com_email_e_senha

import com.alpaca.hyperpong.domain.repository.AuthRepository

class LogarComEmailESenhaUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, senha: String) =
        repository.logarComEmailESenha(email = email, senha = senha)
}