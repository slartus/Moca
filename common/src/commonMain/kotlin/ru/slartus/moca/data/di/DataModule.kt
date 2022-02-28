package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.data.filmix.FilmixApi
import ru.slartus.moca.data.tmdb.TmdbApi
import ru.slartus.moca.domain.repositories.CatalogRepository
import ru.slartus.moca.domain.repositories.CatalogRepositoryImpl


val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton { TmdbApi(instance()) }
    bindSingleton { FilmixApi(instance()) }
}

