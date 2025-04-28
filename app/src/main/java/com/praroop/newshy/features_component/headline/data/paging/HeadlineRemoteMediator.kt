package com.praroop.newshy.features_component.headline.data.paging


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.praroop.newshy.features_component.core.data.local.NewsyArticleDatabase
import com.praroop.newshy.features_component.core.data.remote.models.Article
import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import com.praroop.newshy.features_component.headline.data.local.model.HeadlineRemoteKey
import com.praroop.newshy.features_component.headline.data.remote.HeadlineApi
import okio.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val api: HeadlineApi,
    private val database: NewsyArticleDatabase,
    private val articleheadlineDtoMapper:Mapper<Article,HeadLineDto>,
    private val category: String = "",
    private val country: String = "",
    private val language: String = ""
) : RemoteMediator<Int, HeadLineDto>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.SECONDS)
        return if (
            System.currentTimeMillis() - (database.headlineRemoteDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadLineDto>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey=getRemoteKeyClosestToCurrentposition(state)
                remoteKey?.nextKey?.minus(1)?:1
            }
            LoadType.PREPEND -> {
                val remoteKey=getRemoteKeyForFirstItem(state)
                val prevKey=remoteKey?.prevKey
                prevKey?:return MediatorResult.Success(
                    endOfPaginationReached =  remoteKey !=null
                )
            }
            LoadType.APPEND -> {
                val remoteKey=getRemoteKeyForLastItem(state)
                val nextKey=remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey!=null
                )
            }
        }



        return try {

            val headlineApiResponse = api.getHeadlines(
                category = category,
                country = country,
                language = language,
                page = page,
                pageSize = 20,

            )

            val headlineArticles = headlineApiResponse.articles
            val endOfpagginationReached = headlineArticles.isEmpty()



            database.apply {
                if (loadType == LoadType.REFRESH) {
                    database.apply {
                        headlineRemoteDao().clearRemoteKeys()
                        headlineDao().removeAllHeadlineArticles()
                    }
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfpagginationReached) null else page + 1
                val remoteKey = headlineArticles.map {
                    HeadlineRemoteKey(
                        articleId = it.url!!,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        currentPage = page
                    )

                }
                database.apply {
                    headlineRemoteDao().insertAll(remoteKey)
                    headlineDao().insertHeadlineArticle(
                        article = headlineArticles.map {
                            articleheadlineDtoMapper.toModel(
                                it
                            )
                        }
                    )
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfpagginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        }catch (error:HttpException){
            MediatorResult.Error(error)
        }


    }
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, HeadLineDto>
    ):HeadlineRemoteKey?{
        return state.pages.firstOrNull(){
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.headlineRemoteDao().getRemoteKeyByArticleId(article.url)
        }
    }





    private suspend fun getRemoteKeyClosestToCurrentposition(
        state: PagingState<Int, HeadLineDto>
    ):HeadlineRemoteKey ?{
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let {id->
                database.headlineRemoteDao().getRemoteKeyByArticleId(id)

            }

        }
    }
    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, HeadLineDto>
    ):HeadlineRemoteKey?{
        return state.pages.lastOrNull(){
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {article->
            database.headlineRemoteDao().getRemoteKeyByArticleId(article.url)

        }
    }
}