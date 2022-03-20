package ru.slartus.moca.data.repo

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slartus.moca.db.MocaDatabase
import ru.slartus.moca.domain.models.*
import ru.slartus.moca.domain.repositories.VideoSourcesRepository
import ru.slartus.moca.data.models.Video as DataVideo

class VideoSourcesRepositoryImpl(
    private val client: HttpClient,
    database: MocaDatabase
) :
    VideoSourcesRepository {

    private val queries = database.videoSourcesQueries

    override suspend fun getSources(): List<VideoSource> = withContext(Dispatchers.Default) {
        queries.selectAll().executeAsList().map {
            VideoSource(it.id, it.title, it.url)
        }
    }

    override suspend fun <T : Product> findIn(
        source: VideoSource,
        product: T
    ): List<Video> {
        val url = source.url
            .replace("\$title", product.title)
            .replace("\$original_title", product.originalTitle)
            .replace("\$year", product.year ?: "")
            .replace("\$type", "")// TODO: прописывать тип поиска
        val response: List<DataVideo> = client.get(url)
        return response.map {
            Video(
                source = source.title,
                title = it.title,
                url = it.url
            )
        }
    }


    override suspend fun addSource(source: VideoSource) =
        withContext(Dispatchers.Default) {
            queries.insert(source.title, source.url)
        }

    override suspend fun updateSource(source: VideoSource) =
        withContext(Dispatchers.Default) {
            queries.update(
                source.title,
                source.url,
                source.id!!
            )
        }

    override suspend fun deleteSource(id: Long) =
        withContext(Dispatchers.Default) {
            queries.delete(id)
        }
}