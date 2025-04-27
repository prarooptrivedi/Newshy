package com.praroop.newshy.features_component.headline.domain.use_case

import androidx.paging.PagingData
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.headline.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow

class FetchHeadlineArticleUsecase(private val repository: HeadlineRepository) {
    operator fun invoke(
        category: String,
        countryCode: String,
        languageCode: String
    ):Flow<PagingData<NewsyArticel>> = repository.fetchHeadlineArticle(
        category,countryCode,languageCode
    )
}