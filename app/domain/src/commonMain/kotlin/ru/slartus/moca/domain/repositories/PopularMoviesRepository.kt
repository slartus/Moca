package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import ru.slartus.moca.domain.models.Movie

interface PopularMoviesRepository {
    val items: Flow<List<Movie>>
    suspend fun reload()
    suspend fun loadMore()
}