package com.alpaca.hyperpong.presentation.shared

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.util.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel(), FirebaseAuth.AuthStateListener {
    private val _auth = Firebase.auth
    private val _signUpState: MutableStateFlow<RequestState<FirebaseUser?>> = MutableStateFlow(RequestState.Idle)
    val signUpState = _signUpState.asStateFlow()
    private val _signInState: MutableStateFlow<RequestState<FirebaseUser?>> = MutableStateFlow(RequestState.Idle)
    val signInState = _signInState.asStateFlow()
    private val _isUsuarioLogado = MutableStateFlow(_auth.currentUser != null)
    val isUsuarioLogado = _isUsuarioLogado.asStateFlow()
    private val _database = Firebase.database.reference
    private val _eventos: MutableStateFlow<List<Evento>> = MutableStateFlow(emptyList())

    init {
        _auth.addAuthStateListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        _auth.removeAuthStateListener(this)
    }

    fun cadastrarUsuario(email: String, senha: String) {
        _signUpState.value = RequestState.Loading
        _auth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                _signUpState.value = RequestState.Success(it.user)
            }
            .addOnFailureListener {
                _signUpState.value = RequestState.Error(it)
            }
    }

    fun logarUsuario(email: String, senha: String) {
        _signInState.value = RequestState.Loading
        _auth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                _signInState.value = RequestState.Success(it.user)
            }
            .addOnFailureListener {
                _signInState.value = RequestState.Error(it)
            }
    }

    fun deslogarUsuario() = _auth.signOut()

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        _isUsuarioLogado.value = firebaseAuth.currentUser != null
    }

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