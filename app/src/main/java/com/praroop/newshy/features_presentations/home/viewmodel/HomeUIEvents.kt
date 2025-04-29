package com.praroop.newshy.features_presentations.home.viewmodel

import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.utils.ArticleCategory

sealed class HomeUIEvents {
    object ViewModelClicked : HomeUIEvents()
    data class ArticleClicked(val url: String) : HomeUIEvents()
    data class CategoryChange(val category: ArticleCategory) : HomeUIEvents()
    data class PrefrencePanelToggle(val isOpen: Boolean) : HomeUIEvents()
    data class OnHeadLineFavouriteChange(val articel: NewsyArticel) : HomeUIEvents()
}
