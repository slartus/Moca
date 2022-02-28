package ru.slartus.moca.domain.repositories

import ru.slartus.moca.data.filmix.FilmixApi
import ru.slartus.moca.data.tmdb.TmdbApi
import ru.slartus.moca.domain.models.Movie

class CatalogRepositoryImpl(
    private val tmdbApi: TmdbApi,
    private val filmixApi: FilmixApi
) : CatalogRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        return tmdbApi.Movies().getPopular()
    }
}