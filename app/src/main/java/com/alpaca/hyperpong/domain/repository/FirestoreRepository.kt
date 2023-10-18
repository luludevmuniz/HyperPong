package com.alpaca.hyperpong.domain.repository

import androidx.paging.PagingData
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.domain.model.firestore.User
import com.alpaca.hyperpong.util.Response
import kotlinx.coroutines.flow.Flow

typealias SignInResponse = (Response<Boolean>) -> Unit
typealias GetEventsResponse = Flow<PagingData<Event>>
typealias GetEventResponse = Flow<Response<Event>>
interface FirestoreRepository {
    fun signIn(user: User, onSignInResponse: SignInResponse)

    fun getEvents(): GetEventsResponse

    fun getEvent(id: String):  GetEventResponse
}