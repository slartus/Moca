package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.Serializable

@Serializable
internal data class VideosResponse(
    val results: List<Video>?=null
)