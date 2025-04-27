package com.praroop.newshy.features_component.headline.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {

    @Query("SELECT * FROM headline_table")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadLineDto>

    @Query("SELECT * FROM headline_table WHERE id = :id")
    fun getHeadlineArticle(id: Int): Flow<HeadLineDto?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineArticle(article: List<HeadLineDto>)

    @Query("DELETE FROM headline_table WHERE fabourite = 0")
    suspend fun removeAllHeadlineArticles(): Int

    @Delete
    suspend fun removeFavouriteArticle(headLineDto: HeadLineDto): Int

    @Query("UPDATE headline_table SET fabourite = :isFavourite WHERE id = :id")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int
}
