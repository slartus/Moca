package ru.slartus.moca.data.api.mock.models

import kotlinx.serialization.*

@Serializable
data class Movie(
    val id: String?,
    val title: String?,
    val originalTitle: String?,
)