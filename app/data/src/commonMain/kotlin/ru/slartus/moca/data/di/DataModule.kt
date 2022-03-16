package ru.slartus.moca.data.di

import SqlDelightDriverFactory
import getHttpClient
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.data.api.mock.MockApi
import ru.slartus.moca.data.api.sampleTorrentsApi.SampleTorrentsApi
import ru.slartus.moca.data.api.tmdb.TmdbApi
import ru.slartus.moca.data.repo.*
import ru.slartus.moca.data.utils.DownloadManager
import ru.slartus.moca.data.utils.DownloadManagerImpl
import ru.slartus.moca.db.MocaDatabase
import ru.slartus.moca.domain.TorrentsApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.*

val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = "tmdb") { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }
    bindSingleton<TorrentsApi>(tag = "sample") { SampleTorrentsApi(instance()) }

    bindSingleton {
        val driver = instance<SqlDelightDriverFactory>().createDriver()
        MocaDatabase(driver)
    }

    bindSingleton<ProductsRepository<Movie>>(tag = "movies") {
        MoviesRepositoryImpl(listOf(instance("tmdb")))
    }
    bindSingleton<ProductsRepository<Series>>(tag = "series") {
        SeriesRepositoryImpl(listOf(instance("tmdb")))
    }
    bindSingleton<ProductsRepository<Movie>>(tag = "animation.movies") {
        PopularAnimationMoviesRepositoryImpl(listOf(instance("tmdb")))
    }
    bindSingleton<ProductsRepository<Series>>(tag = "animation.series") {
        PopularAnimationSeriesRepositoryImpl(listOf(instance("tmdb")))
    }

    bindProvider<MovieRepository> {
        MovieRepositoryImpl(instance("tmdb"))
    }

    bindProvider<SerieRepository> {
        SerieRepositoryImpl(instance("tmdb"))
    }

    bindProvider<TorrentsRepository> {
        TorrentsRepositoryImpl(
            listOf(
                instance("sample")
            )
        )
    }

    bindSingleton<SearchRepository<Movie>>(tag = "movies") {
        MoviesSearchRepositoryImpl(listOf(instance("tmdb")))
    }

    bindSingleton<SearchRepository<Series>>(tag = "series") {
        SeriesSearchRepositoryImpl(listOf(instance("tmdb")))
    }

    bindSingleton<SearchRepository<Movie>>(tag = "animation.movies") {
        AnimationMoviesSearchRepositoryImpl(listOf(instance("tmdb")))
    }

    bindSingleton<SearchRepository<Series>>(tag = "animation.series") {
        AnimationSeriesSearchRepositoryImpl(listOf(instance("tmdb")))
    }

    bindProvider<DownloadManager> {
        DownloadManagerImpl(client = instance())
    }

    bindSingleton<TorrentsSourcesRepository> {
        TorrentsSourcesRepositoryImpl(
            database = instance(),
            client = instance(),
            downloadManager = instance()
        )
    }

}

