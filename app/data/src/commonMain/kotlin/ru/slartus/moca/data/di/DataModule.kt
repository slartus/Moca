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
import ru.slartus.moca.domain.repositories.PopularMoviesRepository
import ru.slartus.moca.domain.repositories.PopularSeriesRepository
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

    bindSingleton<PopularMoviesRepository>(tag = "movies") {
        PopularMoviesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<PopularSeriesRepository>(tag = "series") {
        PopularSeriesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<PopularMoviesRepository>(tag = "animation.movies") {
        PopularAnimationMoviesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<PopularSeriesRepository>(tag = "animation.series") {
        PopularAnimationSeriesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }


    bindProvider<TorrentsRepository> {
        TorrentsRepositoryImpl(
            listOf(
                instance("sample")
            )
        )
    }
}

