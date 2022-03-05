package ru.slartus.moca.domain

import ru.slartus.moca.domain.models.Torrent

interface TorrentsApi {
    val name: String
    suspend fun find(title: String): List<Torrent>
}