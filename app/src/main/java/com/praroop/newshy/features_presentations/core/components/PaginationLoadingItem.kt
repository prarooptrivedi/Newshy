package com.praroop.newshy.features_presentations.core.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingState

@Composable
fun PaginationLoadingItem(
    pagingState: LoadState?,
    onError:(e:Throwable)->Unit,
    onLoading:@Composable () -> Unit
) {
    when(pagingState){
        is LoadState.Error ->{
            val error=pagingState.error
            onError(error)
        }
        is LoadState.Loading->{
            onLoading()
        }
        else ->{

        }
    }
}