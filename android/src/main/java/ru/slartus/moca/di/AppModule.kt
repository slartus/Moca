package ru.slartus.moca.di

import PlatformListener
import SqlDelightDriverFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.App

val androidModule = DI.Module("androidModule") {
    bindSingleton { App.instance }
    bindSingleton { SqlDelightDriverFactory(instance()) }
    bindSingleton { PlatformListener(instance()) }
}