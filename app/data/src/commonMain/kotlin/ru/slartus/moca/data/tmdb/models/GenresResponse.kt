package ru.slartus.moca.data.tmdb.models

import kotlinx.serialization.*

@Serializable
data class GenresResponse(val genres: List<Genre>?)