package ru.slartus.moca.features.`feature-settings`.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.features.`feature-settings`.TorrentsSourcesScreenViewModel
import ru.slartus.moca.features.`feature-settings`.VideoSourcesScreenViewModel

val settingsModule = DI.Module("settingsModule") {
    bindSingleton { TorrentsSourcesScreenViewModel(scope = instance(), repository = instance()) }
    bindSingleton { VideoSourcesScreenViewModel(scope = instance(), repository = instance()) }
}