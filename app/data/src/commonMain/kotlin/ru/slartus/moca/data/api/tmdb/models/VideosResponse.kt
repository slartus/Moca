package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.Serializable

@Serializable
data class VideosResponse(
    val results: List<Video>?=null
)