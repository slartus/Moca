package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.MockApi
import ru.slartus.moca.data.api.sampleTorrentsApi.SampleTorrentsApi
import ru.slartus.moca.data.api.tmdb.TmdbApi
import ru.slartus.moca.data.repo.PopularMoviesRepositoryImpl
import ru.slartus.moca.data.repo.PopularSeriesRepositoryImpl
import ru.slartus.moca.data.repo.TorrentsRepositoryImpl
import ru.slartus.moca.domain.TorrentsApi
import ru.slartus.moca.domain.repositories.PopularMoviesRepository
import ru.slartus.moca.domain.repositories.PopularSeriesRepository
import ru.slartus.moca.domain.repositories.TorrentsRepository

val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = "tmdb") { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }
    bindSingleton<TorrentsApi>(tag = "sample") { SampleTorrentsApi(instance()) }

    bindSingleton<PopularMoviesRepository> {
        PopularMoviesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<PopularSeriesRepository> {
        PopularSeriesRepositoryImpl(
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

