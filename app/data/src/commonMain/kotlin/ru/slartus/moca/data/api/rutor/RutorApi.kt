package ru.slartus.moca.data.api.rutor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slartus.moca.domain.TorrentsApi
import ru.slartus.moca.domain.models.Torrent as RepositoryTorrent

class RutorApi(val client: HttpClient) : TorrentsApi {
    override val name: String = "RUTOR"

    override suspend fun find(title: String, originalTitle: String): List<RepositoryTorrent> =
        withContext(Dispatchers.Default) {
            try {
                val responseData: HttpResponse = client.get("$END_POINT/search/$title")

                val responseBody = responseData.readText()
                val trPattern =
                    Regex("""<tr class="(?:tum|gai)">([\s\S]*?)</tr>""", RegexOption.IGNORE_CASE)
                val tdPattern =
                    Regex("""<td[^>]*>([\s\S]*?)</td>""", RegexOption.IGNORE_CASE)

                val titlePattern =
                    Regex(
                        """href="(http://d\.rutor\.info/download/\d+)".*?href="(magnet:[^"]*)"[\s\S]*?<a[^>]*>([^<]*)""",
                        RegexOption.IGNORE_CASE
                    )
                val result = mutableListOf<RepositoryTorrent>()
                trPattern.findAll(responseBody)
                    .mapNotNull {
                        val trBody = it.groups[1]?.value ?: return@mapNotNull null
                        val tds = tdPattern.findAll(trBody).map { m -> m.groups[1]?.value }.toList()
                        val titleTd =
                            titlePattern.findAll(tds[1] ?: "").map { m -> m.groups[1]?.value }
                                .toList()
                        val downloadUrl = titleTd[0] ?: return@mapNotNull null
                        val magnetUrl = titleTd[1]
                        val tdTitle = titleTd[2] ?: return@mapNotNull null

                        RepositoryTorrent(
                            source = name,
                            title = tdTitle,
                            url = downloadUrl
                        )
                    }
                    .toList()
            }catch (ex:Throwable){
                println(ex.toString())
                emptyList()
            }
        }

    companion object {
        private const val END_POINT = "http://rutor.info"

    }
}