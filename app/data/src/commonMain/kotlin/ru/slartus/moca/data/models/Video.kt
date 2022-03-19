package ru.slartus.moca.data.models

import kotlinx.serialization.Serializable


@Serializable
data class Video(
    val title: String,
    val url: String,
)