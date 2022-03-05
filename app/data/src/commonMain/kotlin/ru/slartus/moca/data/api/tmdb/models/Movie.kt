package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*
import ru.slartus.moca.data.api.tmdb.Genres

inline val Movie.isAnimation: Boolean
    get() = this.genre_ids?.contains(Genres.Animation.id) == true

@Serializable
data class Movie(
    val adult: Boolean?,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Float? = null,
    val vote_count: Int? = null
)