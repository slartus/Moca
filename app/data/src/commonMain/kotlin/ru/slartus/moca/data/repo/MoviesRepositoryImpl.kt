package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(
    private val catalogApis: List<CatalogApi>
) : MoviesRepository {
    private val catalog = catalogApis.first()

    override suspend fun getPopular(): List<Movie> {
        return catalog.getPopularMovies()
    }
}

//class TestRepositoryImpl() : CatalogRepository {
//    override suspend fun getPopularMovies(): List<Movie> {
//        error("some error")
//    }
//
//    override suspend fun getPopularTv(): List<Tv> {
//        TODO("Not yet implemented")
//    }
//}