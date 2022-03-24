package ru.slartus.moca.data.api.tmdb

import coroutines.IO
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import ru.slartus.moca.data.api.tmdb.mappers.buildVideoUrl
import ru.slartus.moca.data.api.tmdb.mappers.map
import ru.slartus.moca.data.api.tmdb.models.*
import ru.slartus.moca.domain.CatalogApi
import kotlin.jvm.JvmInline
import ru.slartus.moca.domain.models.Movie as RepositoryMovie
import ru.slartus.moca.domain.models.ProductDetails as RepositoryMovieDetails
import ru.slartus.moca.domain.models.Series as RepositoryTv

class TmdbApi(val client: HttpClient) : CatalogApi {
    override val name = "TMDB"

    override suspend fun getPopularMovies(page: Int): List<RepositoryMovie> {
        val popularParams = FilterParams(
            sortBy = SortBy.PopularityDesc,
            includeAdult = false,
            withoutGenres = listOf(ru.slartus.moca.data.api.tmdb.Genres.Animation)
        )
        return Discover().getMovies(MoviesPage(page), popularParams)
    }

    override suspend fun getPopularTv(page: Int): List<RepositoryTv> {
        val popularParams = FilterParams(
            sortBy = SortBy.PopularityDesc,
            includeAdult = false,
            withoutGenres = listOf(ru.slartus.moca.data.api.tmdb.Genres.Animation)
        )
        return Discover().getTv(MoviesPage(page), popularParams)
    }

    override suspend fun getPopularAnimationMovies(page: Int): List<RepositoryMovie> {
        val popularParams = FilterParams(
            sortBy = SortBy.PopularityDesc,
            includeAdult = false,
            withGenres = listOf(ru.slartus.moca.data.api.tmdb.Genres.Animation)
        )
        return Discover().getMovies(MoviesPage(page), popularParams)
    }

    override suspend fun getPopularAnimationTv(page: Int): List<RepositoryTv> {
        val popularParams = FilterParams(
            sortBy = SortBy.PopularityDesc,
            includeAdult = false,
            withGenres = listOf(ru.slartus.moca.data.api.tmdb.Genres.Animation)
        )
        return Discover().getTv(MoviesPage(page), popularParams)
    }

    override suspend fun getMovieDetails(movieId: String): RepositoryMovieDetails =
        withContext(Dispatchers.IO) {
            val detailsRequest = async { Movies().getDetails(movieId.toInt()) }
            val videosRequest = async { Movies().getVideos(movieId.toInt()) }

            val details = detailsRequest.await()
            val videos = videosRequest.await()

            return@withContext details.copy(videos = videos.mapNotNull { video ->
                val site = video.site ?: return@mapNotNull null
                val key = video.key ?: return@mapNotNull null
                buildVideoUrl(site, key)
            })
        }

    override suspend fun getSeriesDetails(seriesId: String): ru.slartus.moca.domain.models.ProductDetails =
        withContext(Dispatchers.IO) {
            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                Napier.e("getSeriesDetails", throwable)
            }
            val detailsRequest =
                async(SupervisorJob() + coroutineExceptionHandler) { TV().getDetails(seriesId.toInt()) }
            val videosRequest =
                async(SupervisorJob() + coroutineExceptionHandler) { TV().getVideos(seriesId.toInt()) }

            val details = detailsRequest.await()
            val videos = videosRequest.await()

            return@withContext details.copy(videos = videos.mapNotNull { video ->
                val site = video.site ?: return@mapNotNull null
                val key = video.key ?: return@mapNotNull null
                buildVideoUrl(site, key)
            })
        }


    override suspend fun findMovies(query: String): List<ru.slartus.moca.domain.models.Movie> {
        return Search().searchMovies(query, false)
    }

    override suspend fun findSeries(query: String): List<ru.slartus.moca.domain.models.Series> {
        return Search().searchSeries(query, false)
    }

    override suspend fun findAnimationMovies(query: String): List<ru.slartus.moca.domain.models.Movie> {
        return Search().searchMovies(query, true)
    }

    override suspend fun findAnimationSeries(query: String): List<ru.slartus.moca.domain.models.Series> {
        return Search().searchSeries(query, true)
    }

    inner class Genres {
        suspend fun getMovieList(): List<Genre> {
            val genresResponse: GenresResponse =
                client.get("$END_POINT/genre/movie/list?$DEFAULT_PARAMS")
            return genresResponse.genres ?: emptyList()
        }
    }

    inner class Discover {
        suspend fun getMovies(
            page: MoviesPage = MoviesPage(1),
            filterParams: FilterParams
        ): List<RepositoryMovie> {
            val queryParams = filterParams.toQueryParams()
            val genresResponse: PagedResponse<Movie> =
                client.get("$END_POINT/discover/movie?page=${page.page}&$queryParams&$DEFAULT_PARAMS")
            return (genresResponse.results ?: emptyList())
                .mapNotNull { it.map() }
        }

        suspend fun getTv(
            page: MoviesPage = MoviesPage(1),
            filterParams: FilterParams
        ): List<RepositoryTv> {
            val queryParams = filterParams.toQueryParams()
            val genresResponse: PagedResponse<Tv> =
                client.get("$END_POINT/discover/tv?page=${page.page}&$queryParams&$DEFAULT_PARAMS")
            return (genresResponse.results ?: emptyList())
                .mapNotNull { it.map() }
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

        suspend fun getDetails(movieId: Int): RepositoryMovieDetails {
            val response: MovieDetails = client.get("$END_POINT/movie/$movieId?$DEFAULT_PARAMS")
            return response.map()
        }

        internal suspend fun getVideos(movieId: Int): List<Video> {
            val response: VideosResponse =
                client.get("$END_POINT/movie/$movieId/videos?$DEFAULT_PARAMS")
            return response.results ?: emptyList()
        }
    }

    inner class TV {
        suspend fun getPopular(page: MoviesPage = MoviesPage(1)): List<RepositoryTv> {
            val response: PagedResponse<Tv> =
                client.get("$END_POINT/tv/popular?page=${page.page}&$DEFAULT_PARAMS")
            return (response.results ?: emptyList()).mapNotNull { it.map() }
        }

        suspend fun getDetails(seriesId: Int): RepositoryMovieDetails {
            val response: SeriesDetails = client.get("$END_POINT/tv/$seriesId?$DEFAULT_PARAMS")
            return response.map()
        }

        internal suspend fun getVideos(seriesId: Int): List<Video> {
            val response: VideosResponse =
                client.get("$END_POINT/tv/$seriesId/videos?$DEFAULT_PARAMS")
            return response.results ?: emptyList()
        }
    }

    inner class Search {
        suspend fun searchMovies(
            query: String,
            animation: Boolean,
            page: MoviesPage = MoviesPage(1)
        ): List<RepositoryMovie> {
            val searchParams = SearchParams(query = query).toQueryParams()

            val result = mutableListOf<RepositoryMovie>()
            while (true) {
                val response: PagedResponse<Movie> =
                    client.get("$END_POINT/search/movie?page=${page.page}&$searchParams&$DEFAULT_PARAMS")
                val items = (response.results ?: emptyList())
                    .filter { (animation && it.isAnimation) || (!animation && !it.isAnimation) }
                    .mapNotNull { it.map() }
                result.addAll(items)
                if (result.size > 0 || page.page >= response.totalPages ?: 0)
                    break
            }
            return result
        }

        suspend fun searchSeries(
            query: String,
            animation: Boolean,
            page: MoviesPage = MoviesPage(1)
        ): List<RepositoryTv> {
            val result = mutableListOf<RepositoryTv>()
            while (true) {
                val searchParams = SearchParams(query = query).toQueryParams()
                val response: PagedResponse<Tv> =
                    client.get("$END_POINT/search/tv?page=${page.page}&$searchParams&$DEFAULT_PARAMS")
                val items = (response.results ?: emptyList())
                    .filter { (animation && it.isAnimation) || (!animation && !it.isAnimation) }
                    .mapNotNull { it.map() }
                result.addAll(items)
                if (result.size > 0 || page.page >= response.totalPages ?: 0)
                    break
            }
            return result
        }
    }


    companion object {
        private const val END_POINT = "https://api.themoviedb.org/3"
        private const val API_KEY = "9c97850f98d684bace19186d2979504f"
        private const val LANGUAGE = "ru-RU"

        private const val API_PARAM = "api_key=$API_KEY"
        private const val LANGUAGE_PARAM = "language=$LANGUAGE"
        private const val DEFAULT_PARAMS = "$LANGUAGE_PARAM&$API_PARAM"
    }
}


@JvmInline
value class MoviesPage(val page: Int) {
    init {
        require(page in 1..1000)
    }
}