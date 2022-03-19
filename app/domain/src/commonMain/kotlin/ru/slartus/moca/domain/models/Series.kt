package ru.slartus.moca.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Series(
    override val id: String,
    override val title: String,
    override val originalTitle: String,
    override val posterUrl: String? = null,
    override val year: String? = null,
    val rates: List<Rate> = emptyList(),
    val overview: String? = null
) : Product


fun Series.mapToDetails() = ProductDetails(
    id = this.id,
    title = this.title,
    originalTitle = this.originalTitle,
    posterUrl = this.posterUrl,
    year = this.year,
    rates = this.rates.map { it },
    overview = this.overview
)