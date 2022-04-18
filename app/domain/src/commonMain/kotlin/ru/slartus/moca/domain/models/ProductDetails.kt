package ru.slartus.moca.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ProductDetails(
    val id: String,
    val title: String,
    val originalTitle: String,
    val posterUrl: String? = null,
    val year: String? = null,
    val rates: List<Rate> = emptyList(),
    val overview: String? = null,
    val genres: List<Genre> = emptyList(),
    val videos: List<String> = emptyList(),
)