package com.praroop.newshy.features_presentations.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.praroop.newshy.R
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_presentations.core.components.defaultpadding
import com.praroop.newshy.features_presentations.core.components.itemPadding
import com.praroop.newshy.features_presentations.core.components.itemSpacing
import com.praroop.newshy.features_presentations.core.ui.theme.NewshyTheme
import com.praroop.newshy.utils.Utils
import kotlinx.coroutines.delay

@Composable
fun HeadlineItem(
    article: List<NewsyArticel>,
    articleCount: Int,
    onCardClick: (NewsyArticel) -> Unit,
    onViewMoreClick: () -> Unit,
    onFavouriteChange: (NewsyArticel) -> Unit
) {
    val isAutpoScrolling = remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { articleCount }
    )
    val isDragged = pagerState.interactionSource.collectIsDraggedAsState()
    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged.value) {
            isAutpoScrolling.value = false
        } else {
            isAutpoScrolling.value = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < articleCount - 1) currentPage + 1 else 0
                scrollToPage(target)
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(defaultpadding),
            beyondViewportPageCount = 0,
            pageSize = PageSize.Fill,
            pageSpacing = itemSpacing
        ) { page ->
            if (isAutpoScrolling.value) {
                AnimatedContent(
                    targetState = page,
                    label = ""
                ) { index ->
                    HeadlineCard(
                        modifier = Modifier,
                        articel = article[index],
                        onCardClick = onCardClick,
                        onFavouriteCahnge = onFavouriteChange

                    )
                }


            } else {
                HeadlineCard(
                    modifier = Modifier,
                    articel = article[page],
                    onCardClick = onCardClick,
                    onFavouriteCahnge = onFavouriteChange

                )
            }
        }
    }
    Spacer(modifier = Modifier.size(2.dp))
    TextButton(onClick = onViewMoreClick,) {
        Text(
            text = "view more"
        )
    }
}

@Composable
private fun HeadlineCard(

    articel: NewsyArticel,
    onCardClick: (NewsyArticel) -> Unit,
    onFavouriteCahnge: (NewsyArticel) -> Unit,
    modifier: Modifier = Modifier,

) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(articel.urlToImage)
        .crossfade(true)
        .build()
    val favouriteIcon = if (articel.fabourite) Icons.Filled.BookmarkAdded
    else Icons.Default.Bookmark
    Card(
        onClick = { onCardClick(articel) }, modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = imageRequest,
                placeholder = painterResource(R.drawable.ideogram_2_),
                error = painterResource(R.drawable.ideogram_2_),
                contentScale = ContentScale.Crop,
                contentDescription = "news image",
                modifier = Modifier.height(150.dp)
            )
            Text(
                articel.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(itemPadding)
            )
            Row(

            ) {
                Column(modifier = Modifier.padding(itemPadding)) {
                    Text(
                        text = articel.source,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = Utils.formatPublishedAtDate(articel.publishedAt),
                        style = MaterialTheme.typography.bodySmall
                    )

                }
                IconButton(onClick = { onFavouriteCahnge(articel) }) {
                    Icon(
                        imageVector = favouriteIcon,
                        contentDescription = "favourite"

                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PrevHeadLineItem() {
    val article = listOf(NewsyArticel(
            id = 0,
            author = "Lee Ying Shan",
            content = "",
            description = "",
            publishedAt = "",
            source = "",
            title = "",
            url = "",
            urlToImage = "",
            fabourite = false,
            category = "",
            page = 0
        )
    )
    NewshyTheme {
        HeadlineItem( article,2,{},{},{})
    }


}