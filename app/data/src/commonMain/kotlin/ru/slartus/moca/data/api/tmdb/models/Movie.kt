package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*
import ru.slartus.moca.data.api.tmdb.Genres

inline val Movie.isAnimation: Boolean
    get() = this.genre_ids?.contains(Genres.Animation.id) == true

@Serializable
data class Movie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Float?,
    val vote_count: Int?
)