package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.MockApi
import ru.slartus.moca.data.api.tmdb.TmdbApi
import ru.slartus.moca.domain.repositories.CatalogRepository

val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = "tmdb") { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }

    bindSingleton<CatalogRepository> {
        ru.slartus.moca.data.repo.CatalogRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }

    bindSingleton<CatalogRepository>(tag = "test") {
        ru.slartus.moca.data.repo.TestRepositoryImpl()
    }
}

