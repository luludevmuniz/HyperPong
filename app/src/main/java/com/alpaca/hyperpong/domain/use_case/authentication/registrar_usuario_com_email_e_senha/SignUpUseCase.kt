package com.alpaca.hyperpong.domain.use_case.authentication.registrar_usuario_com_email_e_senha

import com.alpaca.hyperpong.domain.model.firestore.User
import com.alpaca.hyperpong.domain.repository.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User) = repository.signUp(user = user)
}