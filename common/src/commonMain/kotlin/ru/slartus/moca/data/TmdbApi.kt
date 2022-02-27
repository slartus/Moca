package ru.slartus.moca.data

import io.ktor.client.*
import io.ktor.client.request.*
import ru.slartus.moca.data.models.Genre
import ru.slartus.moca.data.models.GenresResponse

class TmdbApi(val client: HttpClient) {
    inner class Genres {
        suspend fun getMovieList(): List<Genre> {

            val genresResponse: GenresResponse =
                client.get("$END_POINT/genre/movie/list?language=en-US&api_key=$API_KEY")
            return genresResponse.genres ?: emptyList()
        }
    }

    companion object {
        private const val END_POINT = "https://api.themoviedb.org/3"
        private const val API_KEY = SecretKeys.TMDB_API_KEY
    }
}