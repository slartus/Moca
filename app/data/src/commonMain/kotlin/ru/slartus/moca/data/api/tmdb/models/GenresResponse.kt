package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*

@Serializable
data class GenresResponse(val genres: List<Genre>?)