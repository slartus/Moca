package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import ru.slartus.moca.domain.models.Movie

interface PopularMoviesRepository {
    val movies: SharedFlow<List<Movie>>
    suspend fun reload()
}