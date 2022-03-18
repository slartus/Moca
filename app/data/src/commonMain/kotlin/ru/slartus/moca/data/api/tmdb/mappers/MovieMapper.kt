package ru.slartus.moca.data.api.tmdb.mappers

import ru.slartus.moca.domain.models.Rate
import ru.slartus.moca.data.api.tmdb.models.Movie as DataMovie
import ru.slartus.moca.data.api.tmdb.models.Genre as DataGenre
import ru.slartus.moca.data.api.tmdb.models.MovieDetails as DataMovieDetails
import ru.slartus.moca.domain.models.Genre as RepositoryGenre
import ru.slartus.moca.domain.models.Movie as RepositoryMovie
import ru.slartus.moca.domain.models.ProductDetails as RepositoryMovieDetails

internal fun DataMovie.map(): RepositoryMovie? {
    val id = this.id ?: return null
    val posterPath = this.poster_path
    val rates = if (this.vote_average != null && this.vote_count != null) listOf(
        Rate(
            "Tmdb",
            this.vote_average,
            this.vote_count
        )
    ) else emptyList()

    return RepositoryMovie(
        id = id.toString(),
        title = this.title ?: this.original_title ?: "No title",
        originalTitle = this.original_title ?: this.title ?: "No title",
        posterUrl = if (posterPath == null) null else buildImageUrl(
            PosterSize.W500,
            posterPath
        ),
        year = this.release_date?.take(4),
        rates = rates,
        overview = this.overview
    )
}

internal fun DataMovieDetails.map(): RepositoryMovieDetails {
    val id = this.id!!
    val posterPath = this.poster_path
    val rates = if (this.vote_average != null && this.vote_count != null) listOf(
        Rate(
            "Tmdb",
            this.vote_average,
            this.vote_count
        )
    ) else emptyList()
    val genres: List<RepositoryGenre> = (this.genres ?: emptyList()).map()
    return RepositoryMovieDetails(
        id = id.toString(),
        title = this.title ?: this.original_title ?: "No title",
        originalTitle = this.original_title ?: this.title ?: "No title",
        posterUrl = if (posterPath == null) null else buildImageUrl(
            PosterSize.W342,
            posterPath
        ),
        year = this.release_date?.take(4),
        rates = rates,
        overview = this.overview,
        genres = genres
    )
}

internal fun List<DataGenre>.map() = this
    .filter { it.id != null && it.name != null }
    .map { RepositoryGenre(it.id?.toString()!!, it.name!!) }