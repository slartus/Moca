package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie

class MoviesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : BaseRepository<Movie>(catalogApis) {
    override suspend fun getPageItems(page: Int) = catalog.getPopularMovies(page)
}