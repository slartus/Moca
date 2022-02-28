package ru.slartus.moca.data.tmdb

import io.ktor.client.*
import io.ktor.client.request.*
import ru.slartus.moca.data.tmdb.models.Genre
import ru.slartus.moca.data.tmdb.models.GenresResponse
import ru.slartus.moca.domain.models.Movie as RepositoryMovie
import ru.slartus.moca.data.tmdb.models.PopularResponse

class TmdbApi(val client: HttpClient) {
    inner class Genres {
        suspend fun getMovieList(): List<Genre> {
            val genresResponse: GenresResponse =
                client.get("$END_POINT/genre/movie/list?$DEFAULT_PARAMS")
            return genresResponse.genres ?: emptyList()
        }
    }

    inner class Movies {
        suspend fun getPopular(): List<RepositoryMovie> {
            val genresResponse: PopularResponse =
                client.get("$END_POINT/movie/popular?$DEFAULT_PARAMS")
            return (genresResponse.results ?: emptyList()).mapNotNull {
                val id = it.id ?: return@mapNotNull null
                return@mapNotNull RepositoryMovie(
                    id = id.toString(),
                    title = it.title ?: it.original_title ?: "No title"
                )
            }
        }
    }

    companion object {
        private const val END_POINT = "https://api.themoviedb.org/3"
        private const val API_KEY = "9c97850f98d684bace19186d2979504f"
        private const val LANGUAGE = "ru-RU"
        private const val DEFAULT_PARAMS = "language=$LANGUAGE&api_key=$API_KEY"
    }
}