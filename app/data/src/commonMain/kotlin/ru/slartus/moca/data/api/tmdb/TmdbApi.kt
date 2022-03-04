package ru.slartus.moca.data.api.tmdb

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import ru.slartus.moca.data.api.tmdb.mappers.map
import ru.slartus.moca.data.api.tmdb.models.*
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie as RepositoryMovie
import ru.slartus.moca.domain.models.Series as RepositoryTv
import kotlin.jvm.JvmInline

class TmdbApi(val client: HttpClient) : CatalogApi {
    override val name = "TMDB"

    override suspend fun getPopularMovies(): List<RepositoryMovie> {
        return withContext(Dispatchers.Default) {
            val page1 = async { Movies().getPopular(MoviesPage(1)) }
            val page2 = async { Movies().getPopular(MoviesPage(2)) }
            page1.await() + page2.await()
        }
    }

    override suspend fun getPopularTv(): List<RepositoryTv> {
        return withContext(Dispatchers.Default) {
            val page1 = async { TV().getPopular(MoviesPage(1)) }
            val page2 = async { TV().getPopular(MoviesPage(2)) }
            page1.await() + page2.await()
        }
    }

    inner class Genres {
        suspend fun getMovieList(): List<Genre> {
            val genresResponse: GenresResponse =
                client.get("$END_POINT/genre/movie/list?$DEFAULT_PARAMS")
            return genresResponse.genres ?: emptyList()
        }
    }

    inner class Movies {
        suspend fun getPopular(page: MoviesPage = MoviesPage(1)): List<RepositoryMovie> {
            val genresResponse: PagedResponse<Movie> =
                client.get("$END_POINT/movie/popular?page=${page.page}&$DEFAULT_PARAMS")
            return (genresResponse.results ?: emptyList())
                .filter { !it.isAnimation }
                .mapNotNull { it.map() }
        }
    }

    inner class TV {
        suspend fun getPopular(page: MoviesPage = MoviesPage(1)): List<RepositoryTv> {
            val genresResponse: PagedResponse<Tv> =
                client.get("$END_POINT/tv/popular?page=${page.page}&$DEFAULT_PARAMS")
            return (genresResponse.results ?: emptyList()).mapNotNull { it.map() }
        }
    }


    companion object {
        private const val END_POINT = "https://api.themoviedb.org/3"
        private const val API_KEY = "9c97850f98d684bace19186d2979504f"
        private const val LANGUAGE = "ru-RU"
        private const val DEFAULT_PARAMS = "language=$LANGUAGE&api_key=$API_KEY"
    }
}


@JvmInline
value class MoviesPage(val page: Int) {
    init {
        require(page in 1..1000)
    }
}