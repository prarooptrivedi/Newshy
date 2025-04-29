package com.praroop.newshy.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.praroop.newshy.features_component.core.data.local.NewsyArticleDatabase
import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.discover.data.local.dao.DiscoverArticleDao
import com.praroop.newshy.features_component.discover.data.local.dao.DiscoverRemoteKeyDao
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto
import com.praroop.newshy.features_component.discover.data.mapper.DiscoveryMapper
import com.praroop.newshy.features_component.discover.data.remote.DiscoverApi
import com.praroop.newshy.features_component.discover.data.reposistory.DiscoverRepoImpl
import com.praroop.newshy.features_component.discover.domain.reposistory.DiscoverReposistory
import com.praroop.newshy.features_component.discover.domain.usecase.DiscoverUseCases
import com.praroop.newshy.features_component.discover.domain.usecase.FetchDiscoverArticleUseCase
import com.praroop.newshy.features_component.discover.domain.usecase.GetDiscoverCurrentCategoryUseCase
import com.praroop.newshy.features_component.discover.domain.usecase.UpdateCurrentCategoryUseCase
import com.praroop.newshy.features_component.discover.domain.usecase.UpdateFavouriteDiscoverArticleUseCase
import com.praroop.newshy.utils.K
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideDiscoverApi(): DiscoverApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(DiscoverApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDiscoverReposistory(
        api: DiscoverApi,
        database: NewsyArticleDatabase,
        mapper: Mapper<DiscoverArticleDto, NewsyArticel>
    ): DiscoverReposistory {
        return DiscoverRepoImpl(
            disCoverApi = api,
            database = database,
            mapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideDiscoverArticleDao(
        database: NewsyArticleDatabase
    ): DiscoverArticleDao = database.discoverArticleDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: NewsyArticleDatabase): DiscoverRemoteKeyDao =
        database.discoverRemoteKeyDao()

    @Provides
    @Singleton
    fun provideDiscoverMapper():Mapper<DiscoverArticleDto,NewsyArticel> =
        DiscoveryMapper()

    @Provides
    @Singleton
    fun provideDiscoverUseCases(reposistory: DiscoverReposistory):DiscoverUseCases{
        return DiscoverUseCases(
            fetchDiscoverArticleUseCase = FetchDiscoverArticleUseCase(reposistory),
            updateFavouriteDiscoverArticleUseCase = UpdateFavouriteDiscoverArticleUseCase(reposistory),
            discoverCurrentCategoryUseCase= GetDiscoverCurrentCategoryUseCase(reposistory),
            updateCurrentCategoryUseCase = UpdateCurrentCategoryUseCase(reposistory)
        )
    }
}