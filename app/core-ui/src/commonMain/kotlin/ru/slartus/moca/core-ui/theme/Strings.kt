package ru.slartus.moca.`core-ui`.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppStrings(
    val movies: String,
    val series: String,
    val menu: String,
    val refresh: String,
)

val ruStrings = AppStrings(
    movies = "Фильмы",
    series = "Сериалы",
    menu = "Меню",
    refresh = "Обновить"
)

val enStrings = AppStrings(
    movies = "Movies",
    series = "TV",
    menu = "Menu",
    refresh = "Refresh"
)


val LocalAppStrings = staticCompositionLocalOf<AppStrings> {
    error("No strings provided")
}

enum class Language{
    Ru, En
}