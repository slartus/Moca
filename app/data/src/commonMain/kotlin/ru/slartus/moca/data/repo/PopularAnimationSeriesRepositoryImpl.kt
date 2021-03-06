package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Series

class PopularAnimationSeriesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : BaseRepository<Series>(catalogApis) {
    override suspend fun getPageItems(page: Int) = catalog.getPopularAnimationTv(page)
}