package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Video
import ru.slartus.moca.domain.models.VideoSource

interface VideoSourcesRepository {
    suspend fun getSources(): List<VideoSource>
    suspend fun <T : Product> findIn(source: VideoSource, product: T): List<Video>
    suspend fun addSource(source: VideoSource)
    suspend fun updateSource(source: VideoSource)
    suspend fun deleteSource(id: Long)
}