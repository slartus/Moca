package ru.slartus.moca.data.api.tmdb.mappers

import ru.slartus.moca.data.api.tmdb.models.SeriesDetails as DataDetails
import ru.slartus.moca.domain.models.Genre
import ru.slartus.moca.domain.models.ProductDetails
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
        year = this.first_air_date?.take(4),
        rates = rates,
        overview = this.overview
    )
}

internal fun DataDetails.map(): ProductDetails {
    val id = this.id!!
    val posterPath = this.poster_path
    val rates = if (this.vote_average != null && this.vote_count != null) listOf(
        Rate(
            "Tmdb",
            this.vote_average,
            this.vote_count
        )
    ) else emptyList()
    val genres: List<Genre> = (this.genres ?: emptyList()).map()
    return ProductDetails(
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
