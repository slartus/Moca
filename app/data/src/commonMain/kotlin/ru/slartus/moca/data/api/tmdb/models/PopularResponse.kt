package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*

@Serializable
data class PagedResponse<T>(
    val page: Int? = null,
    val results: List<T>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null
)