package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.PopularSeriesRepository

class PopularAnimationSeriesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : PopularSeriesRepository {
    private val catalog = catalogApis.first()
    private val pagedRepository: PagedRepository<Series> = PagedRepository {
        catalog.getPopularAnimationTv(it)
    }
    override val items = pagedRepository.items
    override suspend fun reload() = pagedRepository.reload()
    override suspend fun loadMore() = pagedRepository.loadMore()
}