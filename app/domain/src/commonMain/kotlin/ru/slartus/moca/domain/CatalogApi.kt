package ru.slartus.moca.domain

import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Tv

interface CatalogApi {
    val name: String
    suspend fun getPopularMovies():List<Movie>
    suspend fun getPopularTv():List<Tv>
}