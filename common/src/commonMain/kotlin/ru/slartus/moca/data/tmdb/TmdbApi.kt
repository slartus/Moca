package ru.slartus.moca.data.tmdb

import io.ktor.client.*
import io.ktor.client.request.*
import ru.slartus.moca.data.tmdb.models.Genre
import ru.slartus.moca.data.tmdb.models.GenresResponse
import ru.slartus.moca.domain.models.Movie as RepositoryMovie
import ru.slartus.moca.data.tmdb.models.PopularResponse
import kotlin.jvm.JvmInline

class TmdbApi(val client: HttpClient) {
    inner class Genres {
        suspend fun getMovieList(): List<Genre> {
            val genresResponse: GenresResponse =
                client.get("$END_POINT/genre/movie/list?$DEFAULT_PARAMS")
            return genresResponse.genres ?: emptyList()
        }
    }

    inner class Movies {
        suspend fun getPopular(page: MoviesPage = MoviesPage(1)): List<RepositoryMovie> {
            val genresResponse: PopularResponse =
                client.get("$END_POINT/movie/popular?page=${page.page}&$DEFAULT_PARAMS")
            return (genresResponse.results ?: emptyList()).mapNotNull {
                val id = it.id ?: return@mapNotNull null
                val posterPath = it.poster_path
                return@mapNotNull RepositoryMovie(
                    id = id.toString(),
                    title = it.title ?: it.original_title ?: "No title",
                    posterUrl = if (posterPath == null) null else getImageUrl(500, posterPath)
                )
            }
        }
    }

    private fun getImageUrl(fileSize: Int, filePath: String): String {
        return "https://image.tmdb.org/t/p/w$fileSize$filePath"
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