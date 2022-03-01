package ru.slartus.moca.domain.models

data class Tv(
    val id: String,
    val title: String,
    val posterUrl: String? = null
)