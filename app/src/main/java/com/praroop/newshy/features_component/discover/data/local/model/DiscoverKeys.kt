package com.praroop.newshy.features_component.discover.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "discover_keys")
data class DiscoverKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("article_id")
    val article_Id:String,
    val prevKey:Int?,
    val currentPage:Int?,
    val nextKey:Int?,
    @ColumnInfo("current_category")
    val currentCategory:String,
    @ColumnInfo("created_at")
    val createdAt:Long=System.currentTimeMillis()
)
