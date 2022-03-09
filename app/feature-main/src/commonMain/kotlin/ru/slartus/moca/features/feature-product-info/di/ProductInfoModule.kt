package ru.slartus.moca.features.`feature-product-info`.di

import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.instance
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.features.`feature-product-info`.MovieScreenViewModel

val productInfoModule = DI.Module("productInfoModule") {
    bindFactory { movie: Movie -> MovieScreenViewModel(movie, instance(), instance()) }
}