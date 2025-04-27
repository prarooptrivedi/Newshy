package com.praroop.newshy.features_component.headline.data.mapper

import com.praroop.newshy.features_component.core.data.remote.models.Article
import com.praroop.newshy.features_component.core.domain.mapper.Mapper
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import okhttp3.internal.checkOffsetAndCount

class ArticleHeadLineDtoMappe(
    private val page:Int=0,
    private val category:String=""
):Mapper<Article,HeadLineDto> {
    override fun toModel(value: Article): HeadLineDto {
        return value.run {
            HeadLineDto(
               author = formatEmpptyValue(author,"author"),
                content = formatEmpptyValue(content,"content"),
                description = formatEmpptyValue(description,"description"),
                publishedAt = publishedAt,
                source = source.name,
                title = title,
                url = url,
                urlToImage = urlToImage,
                page = page,
                category = category,
            )
        }
    }

    override fun fromModel(value: HeadLineDto): Article {
      return value.run {
          Article(
              author, content, description, publishedAt
          )
      }
    }
    private fun formatEmpptyValue(value: String?,default:String=""):String{
        return value?:"Unknown $default"
    }
}