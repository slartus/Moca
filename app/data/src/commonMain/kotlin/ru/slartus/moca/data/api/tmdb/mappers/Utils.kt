package ru.slartus.moca.data.api.tmdb.mappers

internal fun getImageUrl(fileSize: Int, filePath: String): String {
    return "https://image.tmdb.org/t/p/w$fileSize$filePath"
}