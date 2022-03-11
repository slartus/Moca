package ru.slartus.moca.data.api.sampleTorrentsApi.models

data class Torrent(
    val title: String? = null,
    val url: String? = null,
    val size: String? = null,
    val seeds: Int? = null,
    val peers: Int? = null,
    val downloads: Int? = null,
    val date: String? = null,
)