package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Int? = null,
    val logo_path: String? = null,
    val name: String? = null,
    val origin_country: String? = null
)