package com.praroop.newshy.features_presentations.home.viewmodel

import com.praroop.newshy.features_component.core.domain.models.NewsyArticel

sealed class HomeUIEvents {
    object ViewModelClicked : HomeUIEvents()
    data class ArticleClicked(val url: String) : HomeUIEvents()
    data class CategoryChange(val category: String) : HomeUIEvents()
    data class PrefrencePanelToggle(val isOpen: Boolean) : HomeUIEvents()
    data class OnHeadLineFavouriteChange(val articel: NewsyArticel) : HomeUIEvents()
}
