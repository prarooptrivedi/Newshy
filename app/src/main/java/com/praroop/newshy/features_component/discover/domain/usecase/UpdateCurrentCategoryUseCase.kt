package com.praroop.newshy.features_component.discover.domain.usecase

import com.praroop.newshy.features_component.discover.domain.reposistory.DiscoverReposistory

class UpdateCurrentCategoryUseCase(
    private val reposistory: DiscoverReposistory
) {
    suspend operator fun invoke(category:String){
        reposistory.updateCategory(category)
    }
}