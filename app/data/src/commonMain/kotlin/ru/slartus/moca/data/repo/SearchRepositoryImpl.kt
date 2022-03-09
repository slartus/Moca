package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.SearchRepository

class MoviesSearchRepositoryImpl(catalogApis: List<CatalogApi>) : SearchRepository<Movie> {
    private val catalog = catalogApis.first()

    override suspend fun search(query: String): List<Movie> {
        return catalog.findMovies(query)
    }
}

class SeriesSearchRepositoryImpl(catalogApis: List<CatalogApi>) : SearchRepository<Series> {
    private val catalog = catalogApis.first()

    override suspend fun search(query: String): List<Series> {
        return catalog.findSeries(query)
    }
}

class AnimationMoviesSearchRepositoryImpl(catalogApis: List<CatalogApi>) : SearchRepository<Movie> {
    private val catalog = catalogApis.first()

    override suspend fun search(query: String): List<Movie> {
        return catalog.findAnimationMovies(query)
    }
}

class AnimationSeriesSearchRepositoryImpl(catalogApis: List<CatalogApi>) : SearchRepository<Series> {
    private val catalog = catalogApis.first()

    override suspend fun search(query: String): List<Series> {
        return catalog.findAnimationSeries(query)
    }
}