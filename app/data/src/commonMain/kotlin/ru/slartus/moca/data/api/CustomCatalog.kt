package ru.slartus.moca.data.api

import io.ktor.client.*
import io.ktor.client.request.*
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.ProductDetails
import ru.slartus.moca.domain.models.Series

class CustomCatalog(val client: HttpClient, val endpoint: String) : CatalogApi {
    override val name: String = "CustomCatalog"

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return client.get("${endpoint}movies?page=${page}")
    }

    override suspend fun getPopularTv(page: Int): List<Series> {
        return client.get("${endpoint}series?page=${page}")
    }

    override suspend fun getPopularAnimationMovies(page: Int): List<Movie> {
        return client.get("${endpoint}animations?page=${page}")
    }

    override suspend fun getPopularAnimationTv(page: Int): List<Series> {
        return client.get("${endpoint}animation_series?page=${page}")
    }

    override suspend fun getMovieDetails(movieId: String): ProductDetails {
        return client.get("${endpoint}animation/${movieId}")
    }

    override suspend fun getSeriesDetails(seriesId: String): ProductDetails {
        return client.get("${endpoint}series/${seriesId}")
    }

    override suspend fun findMovies(query: String): List<Movie> {
        return client.get("${endpoint}search/movies?query=$query")
    }

    override suspend fun findSeries(query: String): List<Series> {
        return client.get("${endpoint}search/series?query=$query")
    }

    override suspend fun findAnimationMovies(query: String): List<Movie> {
        return client.get("${endpoint}search/animations?query=$query")
    }

    override suspend fun findAnimationSeries(query: String): List<Series> {
        return client.get("${endpoint}search/animation_series?query=$query")
    }
}