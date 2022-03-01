package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie

class CatalogRepositoryImpl(
    private val catalogApis: List<CatalogApi>
) : CatalogRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        return catalogApis.first().getPopularMovies()
    }
}

class TestRepositoryImpl() : CatalogRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        error("some error")
    }
}