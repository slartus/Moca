package ru.slartus.moca.features.`feature-main`.videoGridViews


data class GridViewState<T>(
    val isLoading: Boolean,
    val data: List<T>
)