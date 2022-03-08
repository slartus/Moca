package ru.slartus.moca.data.api.tmdb.mappers

import ru.slartus.moca.data.api.tmdb.models.VideoSite

internal fun buildImageUrl(posterSize: PosterSize, imagePath: String): String {
    return "https://image.tmdb.org/t/p/${posterSize.value}$imagePath"
}

internal fun buildVideoUrl(videoSite: VideoSite, key: String): String {
    return when(videoSite){
        VideoSite.YouTube -> "https://www.youtube.com/watch?v=$key"
        VideoSite.Vimeo -> "https://vimeo.com/$key"
    }
}

internal enum class PosterSize(val value: String) {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    Original("original"),
}