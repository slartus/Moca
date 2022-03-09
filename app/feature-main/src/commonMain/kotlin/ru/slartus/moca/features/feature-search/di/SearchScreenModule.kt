package ru.slartus.moca.features.`feature-search`.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.features.`feature-search`.SearchScreenViewModel

val searchScreenModule = DI.Module("searchScreenModule") {
    bindSingleton (tag = "movies") {
        SearchScreenViewModel<Movie>(
            scope = instance(),
            repository = instance(tag = "movies")
        )
    }

    bindSingleton(tag = "series") {
        SearchScreenViewModel<Series>(
            scope = instance(),
            repository = instance(tag = "series")
        )
    }

    bindSingleton(tag = "animation.movies") {
        SearchScreenViewModel<Movie>(
            scope = instance(),
            repository = instance(tag = "animation.movies")
        )
    }

    bindSingleton(tag = "animation.series") {
        SearchScreenViewModel<Series>(
            scope = instance(),
            repository = instance(tag = "animation.series")
        )
    }
}