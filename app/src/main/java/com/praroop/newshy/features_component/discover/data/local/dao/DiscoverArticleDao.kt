package com.praroop.newshy.features_component.discover.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscoverArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticle(list: List<DiscoverArticleDto>)

    @Query("SELECT * FROM discover_article WHERE category=:category")
    fun getDiscoverArticleDataSource(category: String): PagingSource<Int, DiscoverArticleDto>

    @Query("SELECT * FROM discover_article WHERE id=:id")
    fun getDiscoverArticle(id: Int): Flow<DiscoverArticleDto>

    @Query("DELETE  FROM discover_article WHERE fabourite=0 AND category=:category")
    suspend fun removeAllDiscoverArticle(category: String)

    @Delete
    suspend fun removeFavouriteArticle(discoverArticleDto: DiscoverArticleDto)

    @Query("UPDATE discover_article SET fabourite=:isFavourite WHERE id=:id")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int

}
