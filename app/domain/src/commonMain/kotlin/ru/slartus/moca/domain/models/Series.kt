package ru.slartus.moca.domain.models

data class Series(
    val id: String,
    val title: String,
    val originalTitle: String,
    val posterUrl: String? = null,
    val year: String? = null,
    val rates: List<Rate> = emptyList(),
    val overview: String? = null
)