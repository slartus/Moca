package ru.slartus.moca.data.api.tmdb.mappers

import ru.slartus.moca.domain.models.Rate
import ru.slartus.moca.domain.models.Movie as RepositoryMovie
import ru.slartus.moca.data.api.tmdb.models.Movie as DataMovie

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
        posterUrl = if (posterPath == null) null else getImageUrl(
            500,
            posterPath
        ),
        year = this.release_date,
        rates = rates,
        overview = this.overview
    )
}
