package ru.slartus.moca.features.`feature-main`.videoCardScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.features.`feature-main`.views.*

@Composable
fun MovieScreen(movie: Movie) {
    val strings = LocalAppStrings.current
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = { TopBar(movie.title) }
    ) {
        var tabIndex by remember { mutableStateOf(0) }
        val tabTitles = listOf(strings.description, strings.torrents).map { it.uppercase() }
        Column {
            TabRow(
                backgroundColor = AppTheme.colors.actionBarColor,
                contentColor = AppTheme.colors.primaryText,
                selectedTabIndex = tabIndex
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
            when (tabIndex) {
                0 -> InfoView(movie = movie)
                1 -> TorrentsListView(movie.title, movie.originalTitle)
            }
        }
    }
}

@Composable
private fun InfoView(modifier: Modifier = Modifier, movie: Movie) {
    LazyColumn(modifier = modifier.padding(10.dp).fillMaxSize()) {
        item { Title(movie.title) }
        item { OriginalTitle(movie.originalTitle) }
        item { PosterView(movie.posterUrl, movie.year, movie.rates) }
        item { Description(movie.overview) }
    }
}
