package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.PopularMoviesRepository

class PopularMoviesRepositoryImpl(
    catalogApis: List<CatalogApi>
) : PopularMoviesRepository {
    private val catalog = catalogApis.first()
    private val pagedRepository: PagedRepository<Movie> = PagedRepository {
        catalog.getPopularMovies(it)
    }
    override val items = pagedRepository.items
    override suspend fun reload() = pagedRepository.reload()
    override suspend fun loadMore() = pagedRepository.loadMore()
}