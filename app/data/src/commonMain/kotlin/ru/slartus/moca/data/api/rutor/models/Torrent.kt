package ru.slartus.moca.data.api.rutor.models

data class Torrent(
    val title: String? = null,
    val url: String? = null,
    val size: String? = null,
    val peersUp: Int? = null,
    val peersDown: Int? = null,
)