package com.praroop.newshy.features_component.headline.domain.repository

import androidx.paging.PagingData
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {
    fun fetchHeadlineArticle(category: String,country:String,language:String):
            Flow<PagingData<NewsyArticel>>
    suspend fun updateFavouriteArticle(newsyArticel: NewsyArticel)
}