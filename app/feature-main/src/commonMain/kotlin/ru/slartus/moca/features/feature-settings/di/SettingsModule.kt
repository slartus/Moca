package ru.slartus.moca.features.`feature-settings`.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.features.`feature-settings`.TorrentsSourcesScreenViewModel

val settingsModule = DI.Module("settingsModule") {
    bindSingleton { TorrentsSourcesScreenViewModel(scope = instance(), repository = instance()) }
}