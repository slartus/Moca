package ru.slartus.moca.`core-ui`.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppStrings(
    val movies: String,
    val series: String
)

val ruStrings = AppStrings(
    movies = "Фильмы",
    series = "Сериалы"
)

val enStrings = AppStrings(
    movies = "Movies",
    series = "TV"
)


val LocalAppStrings = staticCompositionLocalOf<AppStrings> {
    error("No strings provided")
}

enum class Language{
    Ru, En
}