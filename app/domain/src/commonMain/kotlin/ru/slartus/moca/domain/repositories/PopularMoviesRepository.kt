package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import ru.slartus.moca.domain.models.Movie

interface PopularMoviesRepository {
    val items: SharedFlow<List<Movie>>
    suspend fun reload()
    suspend fun loadMore()
}