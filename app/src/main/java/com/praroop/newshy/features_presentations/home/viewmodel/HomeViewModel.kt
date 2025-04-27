package com.praroop.newshy.features_presentations.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.praroop.newshy.features_component.headline.domain.use_case.HeadlineUseCases
import com.praroop.newshy.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases
) : ViewModel() {
    var homeState = mutableStateOf(Homestate())
        private set

    init {
        loadArticles()
    }

    private fun loadArticles() {
        homeState.value = homeState.value.copy(
            headlineArticles = headlineUseCases.fetachHeadlibeArticleUseCases(
                category = homeState.value.selectedCategory.category,
                countryCode = Utils.countryCodeList[0].code,
                languageCode = Utils.languageCodeList[0].code
            ).cachedIn(viewModelScope)

        )
    }
    fun onHomeUIEvents(homeUIEvents: HomeUIEvents){
        when(homeUIEvents){
            HomeUIEvents.ViewModelClicked->{}
            is HomeUIEvents.ArticleClicked->{}
            is HomeUIEvents.CategoryChange->{}
            is HomeUIEvents.PrefrencePanelToggle->{}
            is HomeUIEvents.OnHeadLineFavouriteChange->{
               viewModelScope.launch {
                   val isFavourite=homeUIEvents.articel.fabourite
                   val update=homeUIEvents.articel.copy(
                       fabourite = !isFavourite
                   )
                   headlineUseCases.updateHeadlineFavouriteUseCase(
                       update
                   )
               }
            }
        }
    }
}