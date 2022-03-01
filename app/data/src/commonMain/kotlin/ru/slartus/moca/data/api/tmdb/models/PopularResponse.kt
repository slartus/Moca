package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*

@Serializable
data class PopularResponse(val page: Int?, val results: List<Movie>?)