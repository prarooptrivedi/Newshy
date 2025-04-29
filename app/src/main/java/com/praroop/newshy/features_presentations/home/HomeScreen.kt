package com.praroop.newshy.features_presentations.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.SpacerMeasurePolicy.measure
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.praroop.newshy.features_component.core.domain.models.DomainContract
import com.praroop.newshy.features_component.core.domain.models.NewsyArticel
import com.praroop.newshy.features_presentations.core.components.HeaderTitle
import com.praroop.newshy.features_presentations.core.components.PaginationLoadingItem
import com.praroop.newshy.features_presentations.core.components.itemSpacing
import com.praroop.newshy.features_presentations.home.component.DiscoverChips
import com.praroop.newshy.features_presentations.home.component.HeadlineItem
import com.praroop.newshy.features_presentations.home.component.HomeTopAppBar
import com.praroop.newshy.features_presentations.home.viewmodel.HomeUIEvents
import com.praroop.newshy.features_presentations.home.viewmodel.HomeViewModel
import com.praroop.newshy.features_presentations.home.viewmodel.Homestate
import com.praroop.newshy.utils.ArticleCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id:Int) -> Unit,
    onSearchClick: () -> Unit,
    openDrawer: () -> Unit
) {
    val homeState = viewModel.homeState.value
    val headlineArticle = homeState.headlineArticles.collectAsLazyPagingItems()
    val category = ArticleCategory.values()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
                onSearch = onSearchClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            headlineItems(
                headlineArticles = headlineArticle,
                scope=scope,
                snackbarHostState = snackbarHostState,
                onViewMoreClick=onViewMoreClick,
                onHeadlineItemClick = onHeadlineItemClick,
                onFavouriteHeadlineChange = {
                    viewModel.onHomeUIEvents(
                        HomeUIEvents.OnHeadLineFavouriteChange(
                            articel = it
                        )
                    )
                }

            )
        }

    }
}

private fun LazyListScope.headlineItems(
    headlineArticles: LazyPagingItems<NewsyArticel>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    onFavouriteHeadlineChange: (NewsyArticel) -> Unit
) {
    item {
        HeaderTitle(
            title = "Hot News",
            icon = Icons.Filled.LocalFireDepartment
        )
        Spacer(modifier = Modifier.size(itemSpacing))
    }
    item {
        PaginationLoadingItem(
            pagingState = headlineArticles.loadState.mediator?.refresh,
            onError = { e ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = e.message ?: "unknown error"
                    )
                }

            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }
        )

    }
    item {
        val articleList = headlineArticles.itemSnapshotList.items
        HeadlineItem(
            article = articleList,
            articleCount = if (articleList.isEmpty()) 0 else articleList.lastIndex,
            onCardClick = {
                onHeadlineItemClick(it.id)
            },
            onViewMoreClick = onViewMoreClick,
            onFavouriteChange = onFavouriteHeadlineChange
        )
    }
}

@Composable
fun LazyListScope.DiscoverChange(
    homestate: Homestate,
    category: List<ArticleCategory>,
    discoverArticles:LazyPagingItems<NewsyArticel>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onItemClick: (id:Int) -> Unit,
    onCategoryChange:(ArticleCategory)->Unit,
    onFavouriteChange:(article:DomainContract)->Unit,

) {
    item {
        HeaderTitle(
            title = "Discover News",
            icon = Icons.Default.Newspaper
        )
        Spacer(modifier = Modifier.size(itemSpacing))
DiscoverChips(
    selectedCategory = homestate.selectedDiscoverCategory,
    categories = category,
   onCategoryChange =  onCategoryChange

)
    }
    item {
        PaginationLoadingItem(
            pagingState = discoverArticles.loadState.mediator?.refresh,
            onError = {e->
                scope.launch {
                    snackbarHostState.showSnackbar( message = e.message ?: "unknown error")
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(
                            align = Alignment.CenterHorizontally
                        )
                )
            }
        )
    }
    items(count = discoverArticles.itemCount) {index: Int ->


    }
    
}