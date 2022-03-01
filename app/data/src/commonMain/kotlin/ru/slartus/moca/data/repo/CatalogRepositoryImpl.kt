package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.CatalogRepository

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