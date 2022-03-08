package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.slartus.moca.domain.models.Torrent

interface TorrentsRepository {
    val items: Flow<List<Torrent>>
    suspend fun find(title: String, originalTitle: String)
}