package ru.slartus.moca.data.utils

import AppFile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*

interface DownloadManager {
    suspend fun download(url: String, output: AppFile)
}

class DownloadManagerImpl(private val client: HttpClient) : DownloadManager {
    override suspend fun download(url: String, output: AppFile) {
        client.get<HttpStatement>(url).execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.receive()
            val DEFAULT_BUFFER_SIZE = 1024
            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    output.appendBytes(bytes)
                }
            }
        }
    }
}