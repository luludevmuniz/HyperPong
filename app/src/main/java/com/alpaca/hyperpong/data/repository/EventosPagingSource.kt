package com.alpaca.hyperpong.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.util.Constantes.EVENTS_COLLECTION
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await

class EventosPagingSource(private val db: FirebaseFirestore) :
    PagingSource<QuerySnapshot, Event>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Event>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Event> =
        try {
            //TODO: Reimplementar timout
            val queryEventos = db.collection(EVENTS_COLLECTION)
            val currentPage = params.key ?: queryEventos.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size().minus(1)]
            val nextPage = queryEventos.startAfter(lastVisibleProduct).get().await()
            LoadResult.Page(
                data = currentPage.documents.toEventList(),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: TimeoutCancellationException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}

fun List<DocumentSnapshot>.toEventList(): List<Event> =
    map {
        val categoriesAny = it.get("categories")
        val categories = if (categoriesAny is List<*>) {
            categoriesAny.filterIsInstance<HashMap<String, Any>>()
        } else {
            emptyList()
        }

        Event(
            id = it.id,
            description = it.getString("description").orEmpty(),
            image = it.getString("image").orEmpty(),
            status = it.getLong("status")?.toInt(),
            title = it.getString("title").orEmpty(),
            type = it.getLong("type") ?: 0,
            start_date = it.getTimestamp("start_date"),
            end_date = it.getTimestamp("end_date"),
            categories = categories
        )
    }