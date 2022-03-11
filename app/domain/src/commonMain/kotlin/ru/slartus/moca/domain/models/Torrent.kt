package ru.slartus.moca.domain.models

data class Torrent(
    val source: String,
    val title: String,
    val url: String,
    val size: String?,
    val seeds: Int?,
    val peers: Int?,
    val downloads: Int? = null,
    val date: String? = null,
)