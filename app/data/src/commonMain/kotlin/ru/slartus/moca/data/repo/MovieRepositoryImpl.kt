package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.ProductDetails
import ru.slartus.moca.domain.repositories.MovieRepository

class MovieRepositoryImpl(private val catalogApi: CatalogApi) : MovieRepository {
    override suspend fun loadDetails(movieId: String): ProductDetails {
        return catalogApi.getMovieDetails(movieId)
    }
}