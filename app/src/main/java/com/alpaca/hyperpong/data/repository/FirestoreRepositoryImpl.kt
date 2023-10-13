package com.alpaca.hyperpong.data.repository

import com.alpaca.hyperpong.domain.model.firestore.User
import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import com.alpaca.hyperpong.domain.repository.SignInResponse
import com.alpaca.hyperpong.util.ERROR_MESSAGE
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Success
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepositoryImpl(
    private val db: FirebaseFirestore
) : FirestoreRepository {
    override fun signIn(user: User, onSignInResponse: SignInResponse) {
        user.apply {
            val mapUser = mapOf(
                "document" to document,
                "email" to email,
                "name" to name,
                "phone" to phone
            )
            db.collection("users")
                .document(uid)
                .set(mapUser)
                .addOnSuccessListener {
                    onSignInResponse(Success(true))
                }
                .addOnFailureListener { e ->
                    onSignInResponse(Error(e.localizedMessage ?: ERROR_MESSAGE))
                }
        }
    }
}