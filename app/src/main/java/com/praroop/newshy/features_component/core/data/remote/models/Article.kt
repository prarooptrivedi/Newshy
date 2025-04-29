package com.praroop.newshy.features_component.core.data.remote.models


import com.praroop.newshy.features_component.discover.data.local.model.DiscoverArticleDto
import com.praroop.newshy.features_component.headline.data.local.model.HeadLineDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("author")
    val author: String? = null,
    @SerialName("content")
    val content: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("publishedAt")
    val publishedAt: String? = null,
    @SerialName("source")
    val source: Source? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("urlToImage")
    val urlToImage: String? = null
)
fun Article.toDiscoverArticle(page:Int,categoty:String):DiscoverArticleDto{
    return DiscoverArticleDto(
        author = author?:"",
        content = content?:"",
        description = description?:"",
        publishedAt = publishedAt?:"",
        title = title?:"",
        source = source?.name?:"",
        category = categoty?:"",


        url = url?:"",
        urlToImage = urlToImage?:"",
        page = page
    )
}
fun Article.toHeadlineArticle(page: Int,category: String):HeadLineDto{
    return HeadLineDto(
        author = formatEmptyVale(author, "author"),
        content = formatEmptyVale(content, "content"),
        description = formatEmptyVale(description, "description"),
        publishedAt = publishedAt!!,
//                source = source.name,
        source = "source.name",
        title = title!!,
        url = url!!,
        urlToImage = urlToImage?:"",
        page = page,
        category = category,
    )
}

private fun formatEmptyVale(value:String?,default:String):String {
    return value ?: "Unknown $default"
}