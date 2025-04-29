package com.praroop.newshy.features_component.discover.domain.usecase

import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.discover.domain.reposistory.DiscoverReposistory

class UpdateFavouriteDiscoverArticleUseCase(
    private val discoverReposistory: DiscoverReposistory
)  {
    suspend operator fun invoke(article:NewsyArticel){
        discoverReposistory.updateFavouriteDiscoverCategory(article)
    }
}