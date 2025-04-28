package com.praroop.newshy.features_component.discover.data.remote

import com.praroop.newshy.features_component.core.data.remote.models.NewsyRemoteDto
import com.praroop.newshy.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {
    companion object {
        private const val DISCOVER_END_POINT = "/v2/top-headlines"
    }

    @GET(DISCOVER_END_POINT)
    suspend fun getDiscoverHeadlines(
        @Query("api_key") key: String = K.API_KEY,
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): NewsyRemoteDto
}