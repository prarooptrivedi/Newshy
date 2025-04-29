package com.praroop.newshy.features_component.discover.domain.usecase

import androidx.paging.PagingData
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.discover.domain.reposistory.DiscoverReposistory
import kotlinx.coroutines.flow.Flow

class FetchDiscoverArticleUseCase(
    private val reposistory:DiscoverReposistory
) {
    operator fun invoke(
        category:String,
        language:String,
        country:String
    ):Flow<PagingData<NewsyArticel>> = reposistory.fetchDiscoverArticle(category, country, language)
}