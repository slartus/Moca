package ru.slartus.moca.features.`feature-product-info`.di

import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.features.`feature-product-info`.MovieScreenViewModel
import ru.slartus.moca.features.`feature-product-info`.TorrentsListViewModel

val productInfoModule = DI.Module("productInfoModule") {
    bindFactory { movie: Movie ->
        MovieScreenViewModel(
            movie = movie,
            repository = instance(),
            torrentsSourcesRepository = instance(),
            scope = instance()
        )
    }
    bindSingleton {
        TorrentsListViewModel(
            scope = instance(),
            repository = instance()
        )
    }
}