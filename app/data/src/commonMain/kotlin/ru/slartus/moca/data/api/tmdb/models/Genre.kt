package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*

@Serializable
data class Genre(val id: Int? = null, val name: String? = null)