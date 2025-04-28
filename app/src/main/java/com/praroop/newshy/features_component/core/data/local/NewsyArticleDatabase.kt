package com.praroop.newshy.features_component.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.praroop.newshy.features_component.discover.data.local.dao.DiscoverArticleDao
import com.praroop.newshy.features_component.discover.data.local.dao.DiscoverRemoteKeyDao
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverKeys
import com.praroop.newshy.features_component.headline.data.local.dao.HeadLineRemoteKeyDao
import com.praroop.newshy.features_component.headline.data.local.dao.HeadlineDao
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import com.praroop.newshy.features_component.headline.data.local.model.HeadlineRemoteKey

@Database(
    entities = [
        HeadLineDto::class,
        HeadlineRemoteKey::class,
        DiscoverKeys::class,
        DiscoverArticleDto::class
    ],
    version = 1, exportSchema = false
)
abstract class NewsyArticleDatabase : RoomDatabase() {

    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteDao(): HeadLineRemoteKeyDao

    abstract fun discoverArticleDao():DiscoverArticleDao
    abstract fun discoverRemoteKeyDao():DiscoverRemoteKeyDao

}