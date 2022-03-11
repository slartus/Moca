package ru.slartus.moca.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Torrent(
    val title: String,
    @SerialName("torrent_url")
    val url: String,
    val size: String?,
    val seeds: Int?,
    val peers: Int?,
    val downloads: Int? = null,
    val date: String? = null,
)