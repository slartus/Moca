package ru.slartus.moca.domain

import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series

interface CatalogApi {
    val name: String
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getPopularTv(page: Int): List<Series>
}