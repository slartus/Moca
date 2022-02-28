package ru.slartus.moca.domain.models

data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String? = null
)