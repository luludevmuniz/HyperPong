package com.alpaca.hyperpong.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alpaca.hyperpong.domain.model.firestore.Category
import com.alpaca.hyperpong.domain.model.firestore.Event
import com.alpaca.hyperpong.domain.model.firestore.Participant
import com.alpaca.hyperpong.util.Constantes.EVENTS_COLLECTION
import com.google.firebase.Timestamp
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
            val lastVisibleProduct = currentPage.documents[if (currentPage.isEmpty) 0 else currentPage.size().minus(1)]
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

fun HashMap<String, Any>.toCategory(): Category {
    return Category(
        date = get("date") as Timestamp,
        id = get("id") as String,
        name = get("name") as String,
        max_participants = (get("max_participants") as Long).toInt(),
        price = get("price") as String,
        participants = get("participants") as List<Participant>
    )
}
fun List<DocumentSnapshot>.toEventList(): List<Event> =
    map {
        val categories = mutableListOf<Category>()
        (it.get("categories") as List<HashMap<String, Any>>).forEach { category ->
            categories.add(category.toCategory())
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