package com.praroop.newshy.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.praroop.newshy.features_component.core.data.local.NewsyArticleDatabase
import com.praroop.newshy.features_component.core.data.remote.models.Article
import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.headline.data.local.dao.HeadLineRemoteKeyDao
import com.praroop.newshy.features_component.headline.data.local.dao.HeadlineDao
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import com.praroop.newshy.features_component.headline.data.mapper.ArticleHeadLineDtoMappe
import com.praroop.newshy.features_component.headline.data.mapper.HeadlineMapper
import com.praroop.newshy.features_component.headline.data.remote.HeadlineApi
import com.praroop.newshy.features_component.headline.data.repository.HeadlineRepositoryImpl
import com.praroop.newshy.features_component.headline.domain.repository.HeadlineRepository
import com.praroop.newshy.features_component.headline.domain.use_case.FetchHeadlineArticleUsecase
import com.praroop.newshy.features_component.headline.domain.use_case.HeadlineUseCases
import com.praroop.newshy.features_component.headline.domain.use_case.UpdateHeadlineFavouriteUseCase
import com.praroop.newshy.utils.K
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HeadlineModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys
    }

    @Provides
    @Singleton
    fun provideHeadlineApi(): HeadlineApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(HeadlineApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeadlineRepository(
        api: HeadlineApi,
        database: NewsyArticleDatabase,
        mapper: Mapper<HeadLineDto, NewsyArticel>,
        articleHeadlineMapper: Mapper<Article, HeadLineDto>
    ): HeadlineRepository {
        return HeadlineRepositoryImpl(
            headlineApi = api,
            database = database,
            mapper = mapper,
            articleHeadlineMapper = articleHeadlineMapper
        )

    }

    @Provides
    @Singleton
    fun provideHeadlineDao(
        database: NewsyArticleDatabase
    ): HeadlineDao = database.headlineDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(
        database: NewsyArticleDatabase
    ): HeadLineRemoteKeyDao = database.headlineRemoteDao()

    @Provides
    @Singleton
    fun provideHeadlineUseCases(
        repository: HeadlineRepository
    ): HeadlineUseCases =
        HeadlineUseCases(
            fetachHeadlibeArticleUseCases = FetchHeadlineArticleUsecase(
                repository = repository
            ),
            updateHeadlineFavouriteUseCase = UpdateHeadlineFavouriteUseCase(
                repository = repository
            )
        )
    @Provides
    @Singleton
    fun provideArticleToHeadlineMapper():Mapper<Article,HeadLineDto>
    =ArticleHeadLineDtoMappe()

    @Provides
    @Singleton
    fun provideHeadlineMapper():Mapper<HeadLineDto, NewsyArticel>
            =HeadlineMapper()

}