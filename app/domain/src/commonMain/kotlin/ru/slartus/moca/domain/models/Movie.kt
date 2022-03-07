package ru.slartus.moca.domain.models

data class Movie(
    override val id: String,
    override val title: String,
    val originalTitle: String,
    override val posterUrl: String? = null,
    val year: String? = null,
    val rates: List<Rate> = emptyList(),
    val overview: String? = null
):Product

data class Rate(
    val title: String,
    val rate: Float,
    val rateCount: Int
)