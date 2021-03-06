package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.ProductDetails

interface MovieRepository {
    suspend fun loadDetails(movieId: String): ProductDetails
}