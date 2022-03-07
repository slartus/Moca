package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.MovieDetails

interface MovieRepository {
    suspend fun loadDetails(movieId: String): MovieDetails
}