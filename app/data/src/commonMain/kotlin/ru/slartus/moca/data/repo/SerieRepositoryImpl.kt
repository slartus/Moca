package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.ProductDetails
import ru.slartus.moca.domain.repositories.MovieRepository
import ru.slartus.moca.domain.repositories.SerieRepository

class SerieRepositoryImpl(private val catalogApi: CatalogApi) : SerieRepository {
    override suspend fun loadDetails(seriesId: String): ProductDetails {
        return catalogApi.getSeriesDetails(seriesId)
    }
}