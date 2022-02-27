package ru.slartus.moca.data.models

import kotlinx.serialization.*

@Serializable
data class GenresResponse(val genres: List<Genre>?)