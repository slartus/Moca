package ru.slartus.moca.data.di

import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val httpModule = DI.Module("httpModule") {
    bindSingleton { getHttpClient() }
}

