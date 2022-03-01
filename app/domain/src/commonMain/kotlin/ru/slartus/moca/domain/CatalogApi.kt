package ru.slartus.moca.domain

import ru.slartus.moca.domain.models.Movie

interface CatalogApi {
    val name: String
    suspend fun getPopularMovies():List<Movie>
}