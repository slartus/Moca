package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.MockApi
import ru.slartus.moca.data.api.tmdb.TmdbApi
import ru.slartus.moca.data.repo.MoviesRepositoryImpl
import ru.slartus.moca.data.repo.SeriesRepositoryImpl
import ru.slartus.moca.domain.repositories.MoviesRepository
import ru.slartus.moca.domain.repositories.SeriesRepository

val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = "tmdb") { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }

    bindSingleton<MoviesRepository> {
        MoviesRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }
    bindSingleton<SeriesRepository> {
        SeriesRepositoryImpl(
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

