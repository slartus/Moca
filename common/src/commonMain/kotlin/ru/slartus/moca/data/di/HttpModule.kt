package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.data.TmdbApi


val httpModule = DI.Module("httpModule") {
    bindSingleton { getHttpClient() }
    bindSingleton { TmdbApi(instance()) }
}

