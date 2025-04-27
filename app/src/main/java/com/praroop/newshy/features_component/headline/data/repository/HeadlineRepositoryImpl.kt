package com.praroop.newshy.features_component.headline.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.praroop.newshy.features_component.core.data.local.NewsyArticleDatabase
import com.praroop.newshy.features_component.core.data.remote.models.Article
import com.praroop.newshy.features_component.core.domain.mapper.Mapper

import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import com.praroop.newshy.features_component.headline.data.paging.HeadlineRemoteMediator
import com.praroop.newshy.features_component.headline.data.remote.HeadlineApi
import com.praroop.newshy.features_component.headline.domain.repository.HeadlineRepository
import com.praroop.newshy.utils.K
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HeadlineRepositoryImpl(
    private val headlineApi: HeadlineApi,
    private val database: NewsyArticleDatabase,
    private val mapper: Mapper<HeadLineDto, NewsyArticel>,
    private val articleHeadlineMapper: Mapper<Article, HeadLineDto>
) : HeadlineRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchHeadlineArticle(
        category: String,
        country: String,
        language: String
    ): Flow<PagingData<NewsyArticel>> {
        return Pager(
            PagingConfig(
                pageSize = K.PAGE_SIZE,
                prefetchDistance = K.PAGE_SIZE - 1,
                initialLoadSize = 10
            ),
            remoteMediator = HeadlineRemoteMediator(
                api = headlineApi,
                database = database,
                category = category,
                country = country,
                language = language,
                articleheadlineDtoMapper = articleHeadlineMapper
            )
        ) {
            database.headlineDao().getAllHeadlineArticles()
        }.flow.map { dtoPager ->
            dtoPager.map { dto ->
                mapper.toModel(dto)

            }

        }
    }

    override suspend fun updateFavouriteArticle(newsyArticel: NewsyArticel) {
       database.headlineDao().updateFavouriteArticle(
           isFavourite = newsyArticel.fabourite,
           id = newsyArticel.id
       )
    }
}