package com.praroop.newshy.features_component.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.praroop.newshy.features_component.headline.data.local.dao.HeadLineRemoteKeyDao
import com.praroop.newshy.features_component.headline.data.local.dao.HeadlineDao
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import com.praroop.newshy.features_component.headline.data.local.model.HeadlineRemoteKey

@Database(
    entities = [
        HeadLineDto::class,
        HeadlineRemoteKey::class
    ],
    version = 1, exportSchema = false
)
abstract class NewsyArticleDatabase : RoomDatabase() {

    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteDao(): HeadLineRemoteKeyDao
}