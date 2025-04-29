package com.praroop.newshy.features_component.discover.domain.usecase

data class DiscoverUseCases(
    val fetchDiscoverArticleUseCase: FetchDiscoverArticleUseCase,
    val updateCurrentCategoryUseCase: UpdateCurrentCategoryUseCase,
    val discoverCurrentCategoryUseCase: GetDiscoverCurrentCategoryUseCase,
    val updateFavouriteDiscoverArticleUseCase: UpdateFavouriteDiscoverArticleUseCase
)
