package com.praroop.newshy.features_component.discover.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.praroop.newshy.features_component.core.data.local.LocalContractDto

@Entity(tableName = "discover_article")
data class DiscoverArticleDto(
    @PrimaryKey(autoGenerate = true)
    override val id: Int=0,
    override val author: String,
    override val content: String,
    override val description: String,
    @ColumnInfo("published_at")
    override val publishedAt: String,
    override val source: String,
    override val title: String,
    override val url: String,
    @ColumnInfo("uril_to_image")
    override val urlToImage: String,
    override val fabourite: Boolean=false,
    override val category: String,
    override val page: Int

):LocalContractDto()
