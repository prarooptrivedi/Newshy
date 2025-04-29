package com.praroop.newshy.features_component.discover.data.reposistory

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.praroop.newshy.features_component.core.data.local.NewsyArticleDatabase
import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto
import com.praroop.newshy.features_component.discover.data.paging.DiscoverMediator
import com.praroop.newshy.features_component.discover.data.remote.DiscoverApi
import com.praroop.newshy.features_component.discover.domain.reposistory.DiscoverReposistory
import com.praroop.newshy.utils.K
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscoverRepoImpl(
private val disCoverApi:DiscoverApi,
    private val database:NewsyArticleDatabase,
    private val mapper: Mapper<DiscoverArticleDto,NewsyArticel>
):DiscoverReposistory {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchDiscoverArticle(
        category: String,
        country: String,
        language: String
    ): Flow<PagingData<NewsyArticel>> {
        return Pager(
            PagingConfig(
                pageSize = K.PAGE_SIZE,
                initialLoadSize = 10,
                prefetchDistance = K.PAGE_SIZE-1
            ),
            pagingSourceFactory = {
                database.discoverArticleDao().getDiscoverArticleDataSource(category)
            },
            remoteMediator = DiscoverMediator(
                api = disCoverApi,
                dataBase = database,
                category = category,
                country = country,
                language = language
            )

        ).flow.map { dtoPager->
            dtoPager.map {dto->
                mapper.toModel(dto)
            }

        }
    }

    override suspend fun updateCategory(category: String) {
        database.discoverRemoteKeyDao().updateCategory(category)
    }

    override suspend fun getDiscoverCurrentCategory(): String {
       return database.discoverRemoteKeyDao().getCurrentCategory()
    }

    override suspend fun updateFavouriteDiscoverCategory(articel: NewsyArticel) {
        database.discoverArticleDao().updateFavouriteArticle(
            isFavourite = articel.fabourite,
            id = articel.id
        )
    }
}