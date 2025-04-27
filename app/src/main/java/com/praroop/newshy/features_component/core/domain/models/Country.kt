package com.praroop.newshy.features_component.core.domain.models

import androidx.annotation.DrawableRes


data class Country(
    val code:String,
    val name:String,
    @DrawableRes val icResId:Int
)