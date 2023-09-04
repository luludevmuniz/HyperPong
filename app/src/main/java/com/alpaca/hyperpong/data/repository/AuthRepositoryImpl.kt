package com.alpaca.hyperpong.data.repository

import com.alpaca.hyperpong.domain.repository.AuthRepository
import com.alpaca.hyperpong.domain.repository.RetornoAuthState
import com.alpaca.hyperpong.domain.repository.RetornoDeletarUsuario
import com.alpaca.hyperpong.domain.repository.RetornoEnviarEmaiLDeVerificacao
import com.alpaca.hyperpong.domain.repository.RetornoLoginAnonimo
import com.alpaca.hyperpong.domain.repository.RetornoLoginComEmailESenha
import com.alpaca.hyperpong.domain.repository.RetornoRecarregarUsuario
import com.alpaca.hyperpong.domain.repository.RetornoRegistrarComEmailESenha
import com.alpaca.hyperpong.util.ERROR_MESSAGE
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Success
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun registrarUsuarioComEmailESenha(
        email: String,
        senha: String
    ): RetornoRegistrarComEmailESenha {
        return try {
            auth.createUserWithEmailAndPassword(email, senha).await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override fun isUsuarioAutenticado(): Boolean = auth.currentUser != null
    override fun isEmailVerificado(): Boolean = auth.currentUser?.isEmailVerified == true

    override suspend fun logarUsuarioAnonimo(): RetornoLoginAnonimo {
        return try {
            auth.signInAnonymously().await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun logarComEmailESenha(
        email: String,
        senha: String
    ): RetornoLoginComEmailESenha {
        return try {
            auth.signInWithEmailAndPassword(email, senha).await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun enviarEmailDeVerificacao(): RetornoEnviarEmaiLDeVerificacao {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun enviarEmailParaRedefinirSenha(email: String): RetornoDeletarUsuario {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun recarregarUsuario(): RetornoRecarregarUsuario {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override suspend fun deletarUsuario(): RetornoDeletarUsuario {
        return try {
            auth.currentUser?.delete()?.await()
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: ERROR_MESSAGE)
        }
    }

    override fun deslogarUsuario() = auth.signOut()

    override fun getAuthState(viewModelScope: CoroutineScope): RetornoAuthState = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        auth.currentUser == null
    )
}