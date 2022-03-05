package ru.slartus.moca.features.`feature-main`.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.features.`feature-main`.MainScreenViewModel
import ru.slartus.moca.features.`feature-main`.videoGridViews.PopularMoviesViewModel
import ru.slartus.moca.features.`feature-main`.videoGridViews.PopularSeriesViewModel

val mainScreenModule = DI.Module("mainScreenModule") {
    bindSingleton { MainScreenViewModel(instance(), instance()) }
    bindSingleton(tag = "movies") { PopularMoviesViewModel(instance("movies"), instance()) }
    bindSingleton(tag = "series") { PopularSeriesViewModel(instance("series"), instance()) }
    bindSingleton(tag = "animation.movies") { PopularMoviesViewModel(instance("animation.movies"), instance()) }
    bindSingleton(tag = "animation.series") { PopularSeriesViewModel(instance("animation.series"), instance()) }
}

