package ru.slartus.moca.data.mock.models

import kotlinx.serialization.*

@Serializable
data class Movie(
    val id: String?,
    val title: String?
)