package ru.slartus.moca.data.api.tmdb.mappers

import ru.slartus.moca.domain.models.Rate
import ru.slartus.moca.domain.models.Series as RepositoryTv
import ru.slartus.moca.data.api.tmdb.models.Tv as DataTv


internal fun DataTv.map(): RepositoryTv? {
    val id = this.id ?: return null
    val posterPath = this.poster_path
    val rates = if (this.vote_average != null && this.vote_count != null) listOf(
        Rate(
            "Tmdb",
            this.vote_average,
            this.vote_count
        )
    ) else emptyList()
    return RepositoryTv(
        id = id.toString(),
        title = this.name ?: this.original_name ?: "No title",
        originalTitle = this.original_name ?: this.name ?: "No title",
        posterUrl = if (posterPath == null) null else buildImageUrl(
            PosterSize.W342,
            posterPath
        ),
        year = this.first_air_date,
        rates = rates,
        overview = this.overview
    )
}
