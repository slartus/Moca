package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
    val iso_3166_1: String? = null,
    val name: String? = null
)