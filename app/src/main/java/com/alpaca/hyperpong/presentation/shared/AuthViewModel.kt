package com.alpaca.hyperpong.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.domain.use_case.authentication.AuthUseCases
import com.alpaca.hyperpong.util.Response
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    private val _database = Firebase.database.reference
    private val _eventos: MutableStateFlow<List<Evento>> = MutableStateFlow(emptyList())

    init {
        getAuthState()
    }
    val isUsuarioAutenticado get() = authUseCases.isUsuarioAutenticadoUseCase()
    val isEmailVerificado get() = authUseCases.isEmailVerificadoUseCase()
    fun getAuthState() = authUseCases.getAuthStateUseCase(viewModelScope = viewModelScope)
    fun registrarUsuarioComEmailESenha(email: String, senha: String) {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.registrarUsuarioComEmailESenhaUseCase(email = email, senha = senha)
        }
    }

    fun logarComEmailESenha(email: String, senha: String) {
        _response.value = Response.Loading
        viewModelScope.launch {
            _response.value = authUseCases.logarComEmailESenhaUseCase(email = email, senha = senha)
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

    fun getEventos() {
        _database.child("events").get().addOnSuccessListener { retornoApi ->
            val eventos = mutableListOf<Evento>()

            for (snapshot in retornoApi.children) {
                val evento = snapshot.getValue(Evento::class.java)
                evento?.let {
                    eventos.add(it)
                }
            }

            _eventos.value = eventos
        }.addOnFailureListener {
            //TODO...
        }
    }
}