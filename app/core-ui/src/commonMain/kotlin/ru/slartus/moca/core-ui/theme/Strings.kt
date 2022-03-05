package ru.slartus.moca.`core-ui`.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppStrings(
    val movies: String,
    val series: String,
    val menu: String,
    val refresh: String,
    val description: String,
    val torrents: String,
)

val ruStrings = AppStrings(
    movies = "Фильмы",
    series = "Сериалы",
    menu = "Меню",
    refresh = "Обновить",
    description = "Описание",
    torrents = "Торренты"
)

val enStrings = AppStrings(
    movies = "Movies",
    series = "TV",
    menu = "Menu",
    refresh = "Refresh",
    description = "Description",
    torrents = "Torrents"
)


val LocalAppStrings = staticCompositionLocalOf<AppStrings> {
    error("No strings provided")
}

enum class Language {
    Ru, En
}