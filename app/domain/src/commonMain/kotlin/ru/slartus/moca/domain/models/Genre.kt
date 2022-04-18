package ru.slartus.moca.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(val id: String, val title: String)