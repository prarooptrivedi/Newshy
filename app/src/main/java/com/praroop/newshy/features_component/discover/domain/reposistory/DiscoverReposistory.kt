package com.praroop.newshy.features_component.discover.domain.reposistory

import androidx.paging.PagingData
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import kotlinx.coroutines.flow.Flow

interface DiscoverReposistory {
    fun fetchDiscoverArticle(
        category:String,
        country:String,
        language:String
    ):Flow<PagingData<NewsyArticel>>
    suspend fun updateCategory(category:String)
    suspend fun getDiscoverCurrentCategory():String
    suspend fun updateFavouriteDiscoverCategory(articel: NewsyArticel)
}