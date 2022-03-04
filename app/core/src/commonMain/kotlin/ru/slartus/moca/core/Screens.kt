package ru.slartus.moca.core

enum class AppScreenName {
    Main, MovieInfo, SeriesInfo
}

sealed class AppScreen(val screenName: AppScreenName) {
    object MainScreen : AppScreen(AppScreenName.Main)
    data class MovieScreen(val movieId: String) : AppScreen(AppScreenName.MovieInfo)
    data class SeriesScreen(val movieId: String) : AppScreen(AppScreenName.SeriesInfo)
}