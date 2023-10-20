package com.alpaca.hyperpong.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

val CombinedLoadStates.isLoading : Boolean get() =
     prepend is LoadState.Loading || append is LoadState.Loading || refresh is LoadState.Loading

val CombinedLoadStates.isError : Boolean get() =
     prepend is LoadState.Error || refresh is LoadState.Error
