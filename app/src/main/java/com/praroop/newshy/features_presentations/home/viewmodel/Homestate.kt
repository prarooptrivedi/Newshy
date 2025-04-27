package com.praroop.newshy.features_presentations.home.viewmodel

import androidx.paging.PagingData
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.utils.ArticleCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class Homestate (
    val headlineArticles:Flow<PagingData<NewsyArticel>> = emptyFlow(),
    val selectedCategory:ArticleCategory=ArticleCategory.BUSINESS,
    )