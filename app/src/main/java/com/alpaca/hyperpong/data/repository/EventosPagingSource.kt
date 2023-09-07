package com.alpaca.hyperpong.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.util.Constantes.TAMANHO_PAGINA
import com.alpaca.hyperpong.util.Constantes.TABELA_EVENTOS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class EventosPagingSource(private val database: DatabaseReference) :
    PagingSource<DataSnapshot, Evento>() {
    override fun getRefreshKey(state: PagingState<DataSnapshot, Evento>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, Evento> = try {
        val queryEventos = database.child(TABELA_EVENTOS).orderByKey().limitToFirst(TAMANHO_PAGINA)
        val currentPage = params.key ?: queryEventos.get().await()
        val lastVisibleEventKey = currentPage.children.last().key
        val nextPage = queryEventos.startAfter(lastVisibleEventKey).get().await()
        val eventos = currentPage.children.map { snapshot ->
            snapshot.toEvento()
        }
        LoadResult.Page(
            data = eventos,
            prevKey = null,
            nextKey = nextPage
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}

fun DataSnapshot.toEvento(): Evento = getValue(Evento::class.java) ?: Evento()