package ru.slartus.moca.data.api.mock

import io.ktor.client.*
import kotlinx.coroutines.delay
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.models.Movie
import ru.slartus.moca.domain.models.Tv
import ru.slartus.moca.domain.models.Movie as RepositoryMovie

class MockApi(val client: HttpClient) : CatalogApi {
    override val name = "Mock"

    override suspend fun getPopularMovies(): List<RepositoryMovie> = Movies().getPopular()
    override suspend fun getPopularTv(): List<Tv> {
        TODO("Not yet implemented")
    }

    inner class Movies {
        suspend fun getPopular(): List<RepositoryMovie> {
            delay(1000)

            val movies = listOf(Movie("0", "Название","Title"))
            return movies.mapNotNull {
                val id = it.id ?: return@mapNotNull null
                return@mapNotNull RepositoryMovie(
                    id = id,
                    title = it.title ?: "No title",
                    originalTitle = it.originalTitle ?: it.title ?: "No title"
                )
            }
        }
    }
}