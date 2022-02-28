package ru.slartus.moca.data

import ru.slartus.moca.domain.models.Movie

interface CatalogApi {
    val name: String
    suspend fun getPopularMovies():List<Movie>
}