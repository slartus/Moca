package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Tv

interface MoviesRepository {
    suspend fun getPopular(): List<Movie>
}