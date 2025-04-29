package com.praroop.newshy.features_component.discover.data.mapper

import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto

class DiscoveryMapper : Mapper<DiscoverArticleDto, NewsyArticel> {
    override fun toModel(value: DiscoverArticleDto): NewsyArticel {
        return value.run {
            NewsyArticel(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
               url =  url,
                urlToImage = urlToImage,
                fabourite = fabourite,
                category = category,
                page = page
            )
        }
    }

    override fun fromModel(value: NewsyArticel): DiscoverArticleDto {
        return value.run {
            DiscoverArticleDto(
                id,
                author,
                content,
                description,
                publishedAt,
                source,
                title,
                url,
                urlToImage,
                fabourite,
                category,
                page
            )
        }
    }
}