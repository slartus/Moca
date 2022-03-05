package ru.slartus.moca.domain.models

data class Torrent(
    val source: String,
    val title: String,
    val url: String,
    val size: String?,
    val peersUp: Int?,
    val peersDown: Int?,
)