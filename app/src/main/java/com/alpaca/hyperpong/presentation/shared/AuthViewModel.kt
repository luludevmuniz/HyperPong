package com.alpaca.hyperpong.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpaca.hyperpong.domain.model.User
import com.alpaca.hyperpong.domain.use_case.authentication.AuthUseCases
import com.alpaca.hyperpong.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _response: MutableStateFlow<Response<Boolean>> =
        MutableStateFlow(Response.Idle)
    val response = _response.asStateFlow()

    init {
        getAuthState()
    }

    //TODO: Necessário refatorar código para somente logar caso o usuário seja registrado com sucesso no banco
    //TODO: Fazer validação ao logar para ver se o usuário existe no firestore.
    val isUsuarioAutenticado get() = authUseCases.isUsuarioAutenticadoUseCase()
    val isEmailVerificado get() = authUseCases.isEmailVerificadoUseCase()

    //TODO: O que fazer com esse getAuthState?
    fun getAuthState() = authUseCases.getAuthStateUseCase(viewModelScope = viewModelScope)

    fun signUp(user: User) {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.signUp(user = user)
        }
    }

    fun signIn(email: String, password: String) {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.signInUseCase(email = email, password = password)
        }
    }

    fun enviarEmailDeVerificacao() {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.enviarEmailDeVerificacaoUseCase()
        }
    }

    fun enviarEmailResetDeSenha(email: String) {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.enviarEmailParaRedefinirSenhaUseCase(email = email)
        }
    }

    fun recarregarUsuario() {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.recarregarUsuarioUseCase()
        }
    }

    fun deletarUsuario() {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.deletarUsuarioUseCase()
        }
    }

    fun deslogarUsuario() = authUseCases.deslogarUsuarioUseCase()
}