package ru.slartus.moca.domain.models

data class Torrent(
    val source: String,
    val title: String,
    val url: String,
    val size: String? = null,
    val peersUp: Int? = null,
    val peersDown: Int? = null,
)