package ru.slartus.moca.features.`feature-main`.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.features.`feature-main`.MainScreenViewModel
import ru.slartus.moca.features.`feature-main`.videoGridViews.PopularMoviesViewModel
import ru.slartus.moca.features.`feature-main`.videoGridViews.PopularSeriesViewModel

val mainScreenModule = DI.Module("mainScreenModule") {
    bindSingleton { MainScreenViewModel(instance(), instance()) }
    bindSingleton { PopularMoviesViewModel(instance(), instance()) }
    bindSingleton { PopularSeriesViewModel(instance(), instance()) }
}

