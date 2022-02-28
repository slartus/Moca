package ru.slartus.moca.data.filmix.models

import kotlinx.serialization.*

@Serializable
data class Movie(
    val id: String?,
    val title: String?
)