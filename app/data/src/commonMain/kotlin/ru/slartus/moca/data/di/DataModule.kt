package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.MockApi
import ru.slartus.moca.data.api.tmdb.TmdbApi
import ru.slartus.moca.data.repo.PopularMoviesRepositoryImpl
import ru.slartus.moca.data.repo.PopularSeriesRepositoryImpl
import ru.slartus.moca.domain.repositories.PopularMoviesRepository
import ru.slartus.moca.domain.repositories.PopularSeriesRepository

val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = "tmdb") { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }

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
//
//    bindSingleton<CatalogRepository>(tag = "test") {
//        ru.slartus.moca.data.repo.TestRepositoryImpl()
//    }
}

