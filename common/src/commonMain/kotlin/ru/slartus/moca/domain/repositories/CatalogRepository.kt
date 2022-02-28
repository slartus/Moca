package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Movie

interface CatalogRepository {
    suspend fun getPopularMovies():List<Movie>
}