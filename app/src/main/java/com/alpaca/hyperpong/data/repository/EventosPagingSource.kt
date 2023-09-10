package com.alpaca.hyperpong.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.util.Constantes.TABELA_EVENTOS
import com.alpaca.hyperpong.util.Constantes.TAMANHO_PAGINA
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class EventosPagingSource(private val database: DatabaseReference) :
    PagingSource<DataSnapshot, Evento>() {
    override fun getRefreshKey(state: PagingState<DataSnapshot, Evento>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, Evento> =
        try {
            val queryEventos =
                database.child(TABELA_EVENTOS).orderByKey().limitToFirst(TAMANHO_PAGINA)
            val currentPage = params.key ?: withTimeout(5000) {
                queryEventos.get().await()
            }
            val lastVisibleEventKey = currentPage.children.lastOrNull()?.key
            val nextPage = lastVisibleEventKey?.let {
                withTimeout(5000) {
                    queryEventos.startAfter(it).get().await()
                }
            }
            val eventos = currentPage.children.map { snapshot ->
                snapshot.toEvento()
            }
            LoadResult.Page(
                data = eventos,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: TimeoutCancellationException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}

fun DataSnapshot.toEvento(): Evento = getValue(Evento::class.java) ?: Evento()