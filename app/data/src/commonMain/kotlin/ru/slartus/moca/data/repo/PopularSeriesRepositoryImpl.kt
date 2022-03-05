package ru.slartus.moca.data.repo

import kotlinx.coroutines.flow.*
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.PopularSeriesRepository

class PopularSeriesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : PopularSeriesRepository {
    private var page = 1
    private val catalog = catalogApis.first()
    private val _items = MutableStateFlow<List<Series>>(emptyList())
    override val items: SharedFlow<List<Series>> = _items.asStateFlow()
    override suspend fun reload() {
        page = 1
        val items = catalog.getPopularTv(page)
        _items.emit(items)
    }

    override suspend fun loadMore() {
        val items = catalog.getPopularTv(++page)
        val currentItems = _items.value
        val newItems = currentItems + items
        _items.emit(newItems)
    }
}