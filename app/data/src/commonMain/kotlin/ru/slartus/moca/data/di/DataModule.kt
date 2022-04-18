package ru.slartus.moca.data.di

import CatalogScope
import SqlDelightDriverFactory
import getHttpClient
import org.kodein.di.*
import ru.slartus.moca.data.api.CustomCatalog
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

private const val TAG_TMDB = "tmdb"
val dataModule = DI.Module("dataModule") {
    bindSingleton { getHttpClient() }
    bindSingleton<CatalogApi>(tag = TAG_TMDB) { TmdbApi(instance()) }
    bindSingleton<CatalogApi>(tag = "moca_tmdb") { CustomCatalog(instance(), "") }
    bindSingleton<CatalogApi>(tag = "mock") { MockApi(instance()) }
    bindSingleton<TorrentsApi>(tag = "sample") { SampleTorrentsApi(instance()) }

    bindSingleton {
        val driver = instance<SqlDelightDriverFactory>().createDriver()
        MocaDatabase(driver)
    }

    bindSingleton<ProductsRepository<Movie>>(tag = "movies") {
        MoviesRepositoryImpl(listOf(instance(TAG_TMDB)))
    }
    bindSingleton<ProductsRepository<Series>>(tag = "series") {
        SeriesRepositoryImpl(listOf(instance(TAG_TMDB)))
    }
    bindSingleton<ProductsRepository<Movie>>(tag = "animation.movies") {
        PopularAnimationMoviesRepositoryImpl(listOf(instance(TAG_TMDB)))
    }
    bindSingleton<ProductsRepository<Series>>(tag = "animation.series") {
        PopularAnimationSeriesRepositoryImpl(listOf(instance(TAG_TMDB)))
    }

    bindProvider<MovieRepository> {
        MovieRepositoryImpl(instance(TAG_TMDB))
    }

    bindProvider<SerieRepository> {
        SerieRepositoryImpl(instance(TAG_TMDB))
    }

    bindProvider<TorrentsRepository> {
        TorrentsRepositoryImpl(
            listOf(
                instance("sample")
            )
        )
    }

    bindSingleton<SearchRepository<Movie>>(tag = "movies") {
        MoviesSearchRepositoryImpl(listOf(instance(TAG_TMDB)))
    }

    bindSingleton<SearchRepository<Series>>(tag = "series") {
        SeriesSearchRepositoryImpl(listOf(instance(TAG_TMDB)))
    }

    bindSingleton<SearchRepository<Movie>>(tag = "animation.movies") {
        AnimationMoviesSearchRepositoryImpl(listOf(instance(TAG_TMDB)))
    }

    bindSingleton<SearchRepository<Series>>(tag = "animation.series") {
        AnimationSeriesSearchRepositoryImpl(listOf(instance(TAG_TMDB)))
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

    bindSingleton<VideoSourcesRepository> {
        VideoSourcesRepositoryImpl(
            database = instance(),
            client = instance()
        )
    }

    bindSingleton<CatalogSourcesRepository> {
        CatalogSourcesRepositoryImpl(
            database = instance()
        )
    }
}

