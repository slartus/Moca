package ru.slartus.moca.features.`feature-main`.views


internal data class GridViewState<T>(
    val isLoading: Boolean,
    val data: List<T>,
    val error: Exception?
)