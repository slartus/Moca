package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.ProductDetails

interface SerieRepository {
    suspend fun loadDetails(seriesId: String): ProductDetails
}