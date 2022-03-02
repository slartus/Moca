package ru.slartus.moca.features.`feature-main`.videoGridViews


internal data class GridViewState<T>(
    val isLoading: Boolean,
    val data: List<T>
)