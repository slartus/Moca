package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Tv

interface CatalogRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getPopularTv(): List<Tv>
}