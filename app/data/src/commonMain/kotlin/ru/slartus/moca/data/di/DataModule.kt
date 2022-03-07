package ru.slartus.moca.data.di

import SqlDelightDriverFactory
import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.MockApi
import ru.slartus.moca.data.api.sampleTorrentsApi.SampleTorrentsApi
import ru.slartus.moca.data.api.tmdb.TmdbApi
import ru.slartus.moca.data.repo.*
import ru.slartus.moca.db.MocaDatabase
import ru.slartus.moca.domain.TorrentsApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.MovieRepository
import ru.slartus.moca.domain.repositories.ProductsRepository
import ru.slartus.moca.domain.repositories.TorrentsRepository

val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = "tmdb") { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }
    bindSingleton<TorrentsApi>(tag = "sample") { SampleTorrentsApi(instance()) }

    bindSingleton {
        val driver = instance<SqlDelightDriverFactory>().createDriver()
        MocaDatabase(driver)
    }

    bindSingleton<ProductsRepository<Movie>>(tag = "movies") {
        MoviesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<ProductsRepository<Series>>(tag = "series") {
        SeriesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<ProductsRepository<Movie>>(tag = "animation.movies") {
        PopularAnimationMoviesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<ProductsRepository<Series>>(tag = "animation.series") {
        PopularAnimationSeriesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }

    bindProvider<MovieRepository> {
        MovieRepositoryImpl(instance("tmdb"))
    }

    bindProvider<TorrentsRepository> {
        TorrentsRepositoryImpl(
            listOf(
                instance("sample")
            )
        )
    }
}

