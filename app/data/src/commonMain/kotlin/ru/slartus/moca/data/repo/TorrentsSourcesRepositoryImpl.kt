package ru.slartus.moca.data.repo

import AppFile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slartus.moca.data.utils.DownloadManager
import ru.slartus.moca.db.MocaDatabase
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.data.models.Torrent as DataTorrent
import ru.slartus.moca.domain.models.TorrentsSource
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository
import kotlin.math.min

class TorrentsSourcesRepositoryImpl(
    private val client: HttpClient,
    private val database: MocaDatabase,
    private val downloadManager: DownloadManager
) :
    TorrentsSourcesRepository {
    override suspend fun getSources(): List<TorrentsSource> = withContext(Dispatchers.Default) {
        database.torrentsSourcesQueries.selectAll().executeAsList().map {
            TorrentsSource(it.title, it.url)
        }
    }

    override suspend fun <T : Product> findIn(
        source: TorrentsSource,
        product: T
    ): List<Torrent> {
        val url = source.url
            .replace("\$title", product.title)
            .replace("\$original_title", product.originalTitle)
            .replace("\$year", product.year ?: "")
            .replace("\$type", "")// TODO: прописывать тип поиска
        val response: List<DataTorrent> = client.get(url)
        return response.map {
            Torrent(
                source = source.title,
                title = it.title,
                size = it.size,
                seeds = it.seeds,
                peers = it.peers,
                downloads = it.downloads,
                date = it.date,
                url = it.url
            )
        }
    }

    override suspend fun download(torrent: Torrent, output: AppFile) {
        downloadManager.download(torrent.url, output)
    }

    override suspend fun addSource(torrentsSource: TorrentsSource) =
        withContext(Dispatchers.Default) {
            database.torrentsSourcesQueries.insert(torrentsSource.title, torrentsSource.title)
        }
}