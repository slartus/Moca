package ru.slartus.moca.data.api.sampleTorrentsApi

import coroutines.IO
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slartus.moca.data.api.sampleTorrentsApi.models.Torrent
import ru.slartus.moca.domain.TorrentsApi
import ru.slartus.moca.domain.models.Torrent as RepositoryTorrent

class SampleTorrentsApi(val client: HttpClient) : TorrentsApi {
    override val name: String = "FakeSource"

    override suspend fun find(title: String): List<RepositoryTorrent> =
        withContext(Dispatchers.IO) {
            listOf(
                Torrent(
                    null, null, null, null, null
                ),
                Torrent(
                    "Титаник / Titanic (1997) BDRip 1080p",
                    "http://d.rutor.info/download/220284",
                    "11.55 GB",
                    2,
                    0
                ),
                Torrent(
                    "Маленький Немо: Приключения в стране снов / Little Nemo: Adventures in Slumberland (1989) DVDRip",
                    "http://d.rutor.info/download/41427",
                    "3.44 GB",
                    1,
                    0
                )
            )
                .filter { it.title != null && it.url != null }
                .map {
                    RepositoryTorrent(
                        source = name,
                        title = it.title!!,
                        url = it.url!!,
                        size = it.size,
                        seeds = it.seeds,
                        peers = it.peers
                    )
                }
        }
}