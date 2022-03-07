package ru.slartus.moca.domain.models

data class MovieDetails(
    val id: String,
    val title: String,
    val originalTitle: String,
    val posterUrl: String? = null,
    val year: String? = null,
    val rates: List<Rate> = emptyList(),
    val overview: String? = null,
    val genres: List<Genre> = emptyList()
)