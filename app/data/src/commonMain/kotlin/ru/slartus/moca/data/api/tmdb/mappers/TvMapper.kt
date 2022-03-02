package ru.slartus.moca.data.api.tmdb.mappers

import ru.slartus.moca.domain.models.Tv as RepositoryTv
import ru.slartus.moca.data.api.tmdb.models.Tv as DataTv


internal fun DataTv.map(): RepositoryTv? {
    val id = this.id ?: return null
    val posterPath = this.poster_path
    return RepositoryTv(
        id = id.toString(),
        title = this.name ?: this.original_name ?: "No title",
        posterUrl = if (posterPath == null) null else getImageUrl(500, posterPath)
    )
}
