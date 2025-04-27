package com.praroop.newshy.features_component.headline.data.mapper

import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto

class HeadlineMapper : Mapper<HeadLineDto, NewsyArticel> {
    override fun toModel(value: HeadLineDto): NewsyArticel {
        return value.run {
            NewsyArticel(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
                url = url,
                urlToImage = urlToImage,
                fabourite = fabourite,
                category = category,
                page = page
            )
        }
    }

    override fun fromModel(value: NewsyArticel): HeadLineDto {
        return value.run {
            HeadLineDto(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
                url = url,
                urlToImage = urlToImage,
                fabourite = fabourite,
                category = category,
                page = page
            )
        }
    }
}