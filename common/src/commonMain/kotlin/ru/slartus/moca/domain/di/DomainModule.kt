package ru.slartus.moca.domain.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.repositories.CatalogRepository
import ru.slartus.moca.domain.repositories.CatalogRepositoryImpl
import ru.slartus.moca.domain.repositories.TestRepositoryImpl


val domainModule = DI.Module("domainModule") {
    bindSingleton<CatalogRepository> {
        CatalogRepositoryImpl(
            listOf(
                instance("tmdb"),
                // instance("mock")
            )
        )
    }

    bindSingleton<CatalogRepository>(tag = "test") {
        TestRepositoryImpl()
    }
}

