package ru.slartus.moca.features.`feature-main`.di

import org.kodein.di.*
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.features.`feature-main`.MainScreenViewModel
import ru.slartus.moca.features.`feature-main`.videoGridViews.ProductsViewModel

val mainScreenModule = DI.Module("mainScreenModule") {
    bindSingleton { MainScreenViewModel(instance(), instance()) }
    bindSingleton(tag = "movies") { ProductsViewModel<Movie>(instance("movies"), instance()) }
    bindSingleton(tag = "series") { ProductsViewModel<Series>(instance("series"), instance()) }
    bindSingleton(tag = "animation.movies") {
        ProductsViewModel<Movie>(
            instance("animation.movies"),
            instance()
        )
    }
    bindSingleton(tag = "animation.series") {
        ProductsViewModel<Series>(
            instance("animation.series"),
            instance()
        )
    }
}

