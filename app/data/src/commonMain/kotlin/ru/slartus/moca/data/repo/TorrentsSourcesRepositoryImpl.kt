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
import ru.slartus.moca.db.MocaDatabase
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.data.models.Torrent as DataTorrent
import ru.slartus.moca.domain.models.TorrentsSource
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository
import kotlin.math.min

class TorrentsSourcesRepositoryImpl(
    private val client: HttpClient,
    private val database: MocaDatabase
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
        client.get<HttpStatement>(torrent.url).execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.receive()
            val DEFAULT_BUFFER_SIZE = 1024
            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    output.appendBytes(bytes)
                    println("Received ${output.length()} bytes from ${httpResponse.contentLength()}")
                }
            }
            println("A file saved to ${output.path}")
        }
    }
}