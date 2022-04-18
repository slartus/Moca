package ru.slartus.moca.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val source: String,
    val title: String,
    val url: String
)