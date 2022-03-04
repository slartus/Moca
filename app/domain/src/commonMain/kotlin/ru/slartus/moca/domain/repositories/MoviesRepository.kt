package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Movie

interface MoviesRepository {
    suspend fun getPopular(): List<Movie>
}