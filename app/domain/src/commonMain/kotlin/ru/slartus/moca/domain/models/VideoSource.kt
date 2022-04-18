package ru.slartus.moca.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class VideoSource(
    val id: Long? = null,
    val title: String,
    val url: String
)
