package ru.slartus.moca.features.`feature-settings`.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.instance
import ru.slartus.moca.features.`feature-settings`.CatalogSourcesScreenViewModel
import ru.slartus.moca.features.`feature-settings`.TorrentsSourcesScreenViewModel
import ru.slartus.moca.features.`feature-settings`.VideoSourcesScreenViewModel

val settingsModule = DI.Module("settingsModule") {
    bindProvider { TorrentsSourcesScreenViewModel(scope = instance(), repository = instance()) }
    bindProvider { VideoSourcesScreenViewModel(scope = instance(), repository = instance()) }
    bindProvider { CatalogSourcesScreenViewModel(scope = instance(), repository = instance()) }
}