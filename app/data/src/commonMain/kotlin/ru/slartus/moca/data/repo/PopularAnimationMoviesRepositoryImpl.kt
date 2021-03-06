package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie

class PopularAnimationMoviesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : BaseRepository<Movie>(catalogApis) {
    override suspend fun getPageItems(page: Int) = catalog.getPopularAnimationMovies(page)
}