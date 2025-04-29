package com.praroop.newshy.features_component.discover.domain.usecase

import com.praroop.newshy.features_component.discover.domain.reposistory.DiscoverReposistory

class GetDiscoverCurrentCategoryUseCase(
    private val reposistory: DiscoverReposistory
) {
    suspend operator fun invoke():String{
        return reposistory.getDiscoverCurrentCategory()
    }
}