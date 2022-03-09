package ru.slartus.moca.features.`feature-search`.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.features.`feature-search`.SearchScreenViewModel

val searchScreenModule = DI.Module("searchScreenModule") {
    bindSingleton {
        SearchScreenViewModel<Movie>(
            scope = instance(),
            repository = instance(tag = "movies")
        )
    }
}