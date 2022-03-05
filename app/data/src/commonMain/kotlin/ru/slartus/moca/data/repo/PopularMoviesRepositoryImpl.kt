package ru.slartus.moca.data.repo

import kotlinx.coroutines.flow.*
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.PopularMoviesRepository

class PopularMoviesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : PopularMoviesRepository {
    private var page = 1
    private val catalog = catalogApis.first()
    private val _items = MutableStateFlow<List<Movie>>(emptyList())
    override val items: SharedFlow<List<Movie>> = _items.asStateFlow()
    override suspend fun reload() {
        page = 1
        val items = catalog.getPopularMovies(page)
        _items.emit(items)
    }

    override suspend fun loadMore() {
        val items = catalog.getPopularMovies(++page)
        val currentItems = _items.value
        val newItems = currentItems + items
        _items.emit(newItems)
    }
}