package com.alpaca.hyperpong.domain.repository

import com.alpaca.hyperpong.util.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

typealias RetornoLoginAnonimo = Response<Boolean>
typealias RetornoLoginComEmailESenha = Response<Boolean>
typealias RetornoRegistrarComEmailESenha = Response<Boolean>
typealias RetornoEnviarEmaiLDeVerificacao = Response<Boolean>
typealias RetornoRecarregarUsuario = Response<Boolean>
typealias RetornoDeletarUsuario = Response<Boolean>
typealias RetornoEnviarRecuperacaoDeSenha = Response<Boolean>
typealias RetornoAuthState = StateFlow<Boolean>

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun registrarUsuarioComEmailESenha(email: String, senha: String): RetornoRegistrarComEmailESenha

    suspend fun logarUsuarioAnonimo(): RetornoLoginAnonimo

    suspend fun logarComEmailESenha(email: String, senha: String): RetornoLoginComEmailESenha

    suspend fun enviarEmailDeVerificacao(): RetornoEnviarEmaiLDeVerificacao

    suspend fun enviarEmailParaRedefinirSenha(email: String): RetornoEnviarRecuperacaoDeSenha

    suspend fun recarregarUsuario(): RetornoRecarregarUsuario

    suspend fun deletarUsuario(): RetornoDeletarUsuario

    fun isUsuarioAutenticado(): Boolean

    fun isEmailVerificado(): Boolean

    fun deslogarUsuario()

    fun getAuthState(viewModelScope: CoroutineScope): RetornoAuthState
}