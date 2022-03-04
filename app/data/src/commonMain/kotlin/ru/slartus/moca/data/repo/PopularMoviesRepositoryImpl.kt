package ru.slartus.moca.data.repo

import kotlinx.coroutines.flow.*
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.PopularMoviesRepository

class PopularMoviesRepositoryImpl(
    private val catalogApis: List<CatalogApi>
) : PopularMoviesRepository {
    private val catalog = catalogApis.first()
    private val _movies = MutableSharedFlow<List<Movie>>()
    override val movies: SharedFlow<List<Movie>> = _movies.asSharedFlow()
    override suspend fun reload() {
        val movies = catalog.getPopularMovies()
        _movies.emit(movies)
    }
}