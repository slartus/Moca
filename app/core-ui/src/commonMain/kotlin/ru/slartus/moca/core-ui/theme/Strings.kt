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
    val back: String,
    val clear: String,
    val settings: String,
    val title: String,
    val url: String,
    val torrentsSources: String,
    val addTorrentSource: String,
    val delete: String,
    val add: String
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
    clear = "Очистить",
    settings = "Настройки",
    title = "Название",
    url = "Url",
    torrentsSources = "Источники торрентов",
    addTorrentSource = "Добавить источник торрентов",
    delete = "Удалить",
    add = "Добавить"
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
    clear = "Clear",
    settings = "Settings",
    title = "Title",
    url = "Url",
    torrentsSources = "Torrents sources",
    addTorrentSource = "Add torrents source",
    delete = "Delete",
    add = "Add"
)


val LocalAppStrings = staticCompositionLocalOf<AppStrings> {
    error("No strings provided")
}

enum class Language {
    Ru, En
}