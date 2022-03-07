package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val iso_639_1: String? = null,
    val name: String? = null
)