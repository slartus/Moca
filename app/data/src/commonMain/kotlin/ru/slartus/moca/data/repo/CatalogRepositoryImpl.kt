package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Tv
import ru.slartus.moca.domain.repositories.CatalogRepository

class CatalogRepositoryImpl(
    private val catalogApis: List<CatalogApi>
) : CatalogRepository {
    private val calalog = catalogApis.first()

    override suspend fun getPopularMovies(): List<Movie> {
        return calalog.getPopularMovies()
    }

    override suspend fun getPopularTv(): List<Tv> {
        return calalog.getPopularTv()
    }
}

class TestRepositoryImpl() : CatalogRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        error("some error")
    }

    override suspend fun getPopularTv(): List<Tv> {
        TODO("Not yet implemented")
    }
}