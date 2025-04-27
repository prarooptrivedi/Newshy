package com.praroop.newshy.features_component.headline.domain.use_case

import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.headline.domain.repository.HeadlineRepository

class UpdateHeadlineFavouriteUseCase(
    private val repository:HeadlineRepository
) {
    suspend operator fun invoke(articel: NewsyArticel){
        repository.updateFavouriteArticle(articel)
    }
}