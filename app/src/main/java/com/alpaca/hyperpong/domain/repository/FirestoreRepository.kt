package com.alpaca.hyperpong.domain.repository

import com.alpaca.hyperpong.domain.model.User
import com.alpaca.hyperpong.util.Response

typealias SignInResponse = (Response<Boolean>) -> Unit
interface FirestoreRepository {
    fun signIn(user: User, onSignInResponse: SignInResponse)
}