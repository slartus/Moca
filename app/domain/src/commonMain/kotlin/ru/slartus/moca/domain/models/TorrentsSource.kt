package ru.slartus.moca.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class TorrentsSource(
    val id: Long? = null,
    val title: String,
    val url: String
)
