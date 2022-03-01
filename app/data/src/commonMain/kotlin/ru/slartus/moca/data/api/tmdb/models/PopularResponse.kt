package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*

@Serializable
data class PagedResponse<T>(val page: Int?, val results: List<T>?)