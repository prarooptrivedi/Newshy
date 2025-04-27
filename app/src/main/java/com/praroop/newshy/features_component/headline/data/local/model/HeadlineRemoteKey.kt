package com.praroop.newshy.features_component.headline.data.local.model

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("headline_key")
data class  HeadlineRemoteKey (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="article_id")
    val articleId:String,
    val prevKey:Int?,
    val currentPage:Int?,
    val nextKey:Int?,
    @ColumnInfo(name = "created_at")
    val createdAt:Long=System.currentTimeMillis()
)