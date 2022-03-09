package ru.slartus.moca.`core-ui`.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppStrings(
    val movies: String,
    val series: String,
    val animationMovies: String,
    val animationSeries: String,
    val menu: String,
    val refresh: String,
    val description: String,
    val torrents: String,
    val search: String,
    val back:String,
    val clear: String,
)

val ruStrings = AppStrings(
    movies = "Фильмы",
    series = "Сериалы",
    animationMovies = "Мультфильмы",
    animationSeries = "Мультсериалы",
    menu = "Меню",
    refresh = "Обновить",
    description = "Описание",
    torrents = "Торренты",
    search = "Поиск",
    back = "Назад",
    clear = "Очистить"
)

val enStrings = AppStrings(
    movies = "Movies",
    series = "TV",
    animationMovies = "Cartoons",
    animationSeries = "TV Cartoons",
    menu = "Menu",
    refresh = "Refresh",
    description = "Description",
    torrents = "Torrents",
    search = "Search",
    back = "Back",
    clear = "Clear"
)


val LocalAppStrings = staticCompositionLocalOf<AppStrings> {
    error("No strings provided")
}

enum class Language {
    Ru, En
}