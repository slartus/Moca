package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.*

@Serializable
data class Tv(
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Float?,
    val poster_path: String?,
    val vote_average: Float?,
    val vote_count: Int?
)