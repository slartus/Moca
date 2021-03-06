package ru.slartus.moca.data.api.mock

import io.ktor.client.*
import kotlinx.coroutines.delay
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.models.Movie
import ru.slartus.moca.domain.models.ProductDetails
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.models.Movie as RepositoryMovie

class MockApi(val client: HttpClient) : CatalogApi {
    override val name = "Mock"

    override suspend fun getPopularMovies(page: Int): List<RepositoryMovie> = Movies().getPopular()
    override suspend fun getPopularTv(page: Int): List<Series> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularAnimationMovies(page: Int): List<ru.slartus.moca.domain.models.Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularAnimationTv(page: Int): List<Series> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(movieId: String): ProductDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesDetails(seriesId: String): ProductDetails {
        TODO("Not yet implemented")
    }

    override suspend fun findMovies(query: String): List<ru.slartus.moca.domain.models.Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun findSeries(query: String): List<Series> {
        TODO("Not yet implemented")
    }

    override suspend fun findAnimationMovies(query: String): List<ru.slartus.moca.domain.models.Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun findAnimationSeries(query: String): List<Series> {
        TODO("Not yet implemented")
    }

    inner class Movies {
        suspend fun getPopular(): List<RepositoryMovie> {
            delay(1000)

            val movies = listOf(Movie("0", "Название", "Title"))
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