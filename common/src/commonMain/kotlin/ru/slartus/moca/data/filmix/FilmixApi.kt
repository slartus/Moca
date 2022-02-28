package ru.slartus.moca.data.filmix

import io.ktor.client.*
import kotlinx.coroutines.delay
import ru.slartus.moca.data.filmix.models.Movie
import ru.slartus.moca.domain.models.Movie as RepositoryMovie

class FilmixApi(val client: HttpClient) {
    inner class Movies {
        suspend fun getPopular(): List<RepositoryMovie> {
            delay(1000)

            val movies = listOf(Movie("0", "test1"))
            return movies.mapNotNull {
                val id = it.id ?: return@mapNotNull null
                return@mapNotNull RepositoryMovie(
                    id = id,
                    title = it.title ?: "No title"
                )
            }
        }
    }

    companion object {
        private const val END_POINT = "http://filmix.net"
        private const val END_POINT_MOBILE = "http://m.filmix.net"


    }
}