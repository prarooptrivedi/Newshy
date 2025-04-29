package com.praroop.newshy.features_component.discover.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.praroop.newshy.features_component.core.data.local.NewsyArticleDatabase
import com.praroop.newshy.features_component.core.data.remote.models.toDiscoverArticle
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverKeys
import com.praroop.newshy.features_component.discover.data.remote.DiscoverApi
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import com.praroop.newshy.features_component.headline.data.local.model.HeadlineRemoteKey
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class DiscoverMediator(
    private val api: DiscoverApi,
    private val dataBase: NewsyArticleDatabase,
    private val category: String = "",
    private val language: String = "",
    private val country: String = "",
) : RemoteMediator<Int, DiscoverArticleDto>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeOut = TimeUnit.MICROSECONDS.convert(20, TimeUnit.MINUTES)
        val isCacheTimeOut =
            System.currentTimeMillis() - (dataBase.discoverRemoteKeyDao().getCreationTime()
                ?: 0) < cacheTimeOut
        return if (isCacheTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DiscoverArticleDto>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentposition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }
        }
        return try {
            val discoverArticleApiResponse = api.getDiscoverHeadlines(
                category = category,
                page = page,
                country = country,
                language = language,
                pageSize = state.config.pageSize
            )
            val discoverArticles = discoverArticleApiResponse.articles
            val endOfPaginationReached = discoverArticles.isEmpty()
            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.discoverRemoteKeyDao().clearRemoteKey(category)
                    dataBase.discoverArticleDao().removeAllDiscoverArticle(
                        category
                    )
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remotekeys = discoverArticles.map {
                        DiscoverKeys(
                            article_Id = it.url ?: "",
                            prevKey = prevKey,
                            nextKey = nextKey,
                            currentPage = page,
                            currentCategory = category
                        )
                    }
                    dataBase.discoverRemoteKeyDao().insertAllKeys(remotekeys)
                    dataBase.discoverArticleDao().insertAllArticle(list = discoverArticles.map {
                        it.toDiscoverArticle(page, category)
                    })
                }
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        } catch (error: HttpException) {
            MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentposition(
        state: PagingState<Int, DiscoverArticleDto>
    ): DiscoverKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                dataBase.discoverRemoteKeyDao().getRemoteKeyByArticleId(id)

            }

        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, DiscoverArticleDto>
    ): DiscoverKeys? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            dataBase.discoverRemoteKeyDao().getRemoteKeyByArticleId(article.url)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, DiscoverArticleDto>
    ): DiscoverKeys? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            dataBase.discoverRemoteKeyDao().getRemoteKeyByArticleId(article.url)

        }
    }


}