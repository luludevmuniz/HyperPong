package com.alpaca.hyperpong.presentation.common

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.util.RequestState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AuthViewModel : ViewModel() {
    private val _auth = Firebase.auth
    private val _currentUser = _auth.currentUser
    private val _signInRespose: MutableStateFlow<RequestState<FirebaseUser?>> = MutableStateFlow(RequestState.Idle)
    val signInRespose = _signInRespose.asStateFlow()
    val isUsuarioLogado = _currentUser != null


    fun cadastrarUsuario(email: String, senha: String) {
        _auth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                _signInRespose.value = RequestState.Success(it.user)
            }
            .addOnFailureListener {
                _signInRespose.value = RequestState.Error(it)
            }
    }
}