package ru.slartus.moca.domain

import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.MovieDetails
import ru.slartus.moca.domain.models.Series

interface CatalogApi {
    val name: String
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getPopularTv(page: Int): List<Series>
    suspend fun getPopularAnimationMovies(page: Int): List<Movie>
    suspend fun getPopularAnimationTv(page: Int): List<Series>
    suspend fun getMovieDetails(movieId: String): MovieDetails
    suspend fun findMovies(query: String): List<Movie>
    suspend fun findSeries(query: String): List<Series>

    suspend fun findAnimationMovies(query: String): List<Movie>
    suspend fun findAnimationSeries(query: String): List<Series>
}