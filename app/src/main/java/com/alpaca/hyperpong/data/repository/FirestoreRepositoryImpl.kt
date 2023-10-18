package com.alpaca.hyperpong.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.domain.model.firestore.User
import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import com.alpaca.hyperpong.domain.repository.GetEventResponse
import com.alpaca.hyperpong.domain.repository.GetEventsResponse
import com.alpaca.hyperpong.domain.repository.SignInResponse
import com.alpaca.hyperpong.util.Constantes.EVENTS_COLLECTION
import com.alpaca.hyperpong.util.ERROR_MESSAGE
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Success
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val source: EventosPagingSource,
    private val config: PagingConfig
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

    override fun getEvents(): GetEventsResponse =
        Pager(
            config = config
        ) {
            source
        }.flow

    override fun getEvent(id: String): GetEventResponse = callbackFlow {
        val documentReference = db.collection(EVENTS_COLLECTION).document(id)
        val listenerRegistration = documentReference.addSnapshotListener { value, error ->
            if (error == null) {
                value?.toObject(Event::class.java)?.let {
                    trySend(Success(it))
                } ?: trySend(Error(error = "Erro ao converter documento para objeto Evento"))
            } else {
                trySend(Error(error = error.localizedMessage.orEmpty()))
            }
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }
}