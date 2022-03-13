package ru.slartus.moca.domain.repositories

import AppFile
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.domain.models.TorrentsSource

interface TorrentsSourcesRepository {
    suspend fun getSources(): List<TorrentsSource>
    suspend fun <T : Product> findIn(source: TorrentsSource, product: T): List<Torrent>
    suspend fun download(torrent: Torrent, output: AppFile)
    suspend fun addSource(torrentsSource: TorrentsSource)
    suspend fun deleteSource(torrentSource: TorrentsSource)
}