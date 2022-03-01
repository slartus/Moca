package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Tv
import ru.slartus.moca.domain.repositories.SeriesRepository

class SeriesRepositoryImpl(
    private val catalogApis: List<CatalogApi>
) : SeriesRepository {
    private val catalog = catalogApis.first()

    override suspend fun getPopular(): List<Tv> {
        return catalog.getPopularTv()
    }
}