package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.domain.models.TorrentsSource

interface TorrentsSourcesRepository {
    suspend fun getSources(): List<TorrentsSource>
    suspend fun <T : Product> findIn(source: TorrentsSource, product: T): List<Torrent>
}