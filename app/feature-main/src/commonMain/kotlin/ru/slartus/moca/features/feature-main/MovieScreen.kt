package ru.slartus.moca.features.`feature-main`

import AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Rate
import ru.slartus.moca.features.`feature-main`.views.*
import ru.slartus.moca.features.`feature-main`.views.TopBar

@Composable
fun MovieScreen(movie: Movie) {
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = { TopBar(movie.title) }
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp).fillMaxSize()) {
            item { Title(movie.title) }
            item { OriginalTitle(movie.originalTitle) }
            item { PosterView(movie.posterUrl,movie.year, movie.rates) }
            item { Description(movie.overview) }
        }
    }
}
