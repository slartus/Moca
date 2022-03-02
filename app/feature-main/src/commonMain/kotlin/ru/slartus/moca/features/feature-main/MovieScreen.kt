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
import ru.slartus.moca.features.`feature-main`.views.DrawerView
import ru.slartus.moca.features.`feature-main`.views.MainTopBarView
import ru.slartus.moca.features.`feature-main`.views.customDrawerShape

@Composable
fun MovieScreen(movie: Movie) {
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = { TopBar(movie) }
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp).fillMaxSize()) {
            item { Title(movie) }
            item { OriginalTitle(movie) }
            item { PosterView(movie) }
            item { Description(movie) }
        }
    }
}

@Composable
private fun TopBar(movie: Movie) {
    val rootController = LocalRootController.current
    TopBarView(
        title = movie.title,
        navigationIcon = {
            AppNavigationIcon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            ) {
                rootController.popBackStack()
            }
        }
    )
}

@Composable
private fun Title(movie: Movie) {
    Text(
        modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
        text = movie.title,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.strongText
    )
}

@Composable
private fun OriginalTitle(movie: Movie) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = movie.originalTitle,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.secondaryText
    )
}

@Composable
private fun PosterView(movie: Movie) {
    Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.4f)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((maxWidth.value * 1.5).dp),
                imageUrl = movie.posterUrl ?: "",
                contentDescription = "Poster",
                contentScale = ContentScale.FillWidth
            )
        }
        Column(modifier = Modifier.padding(start = 8.dp)) {
            movie.year?.let { year ->
                CategoryText("Год:", year)
            }
            movie.rates.forEach { rate ->
                CategoryText("${rate.title}:", "${rate.rate} (${rate.rateCount})")
            }
        }
    }
}

@Composable
private fun Description(movie: Movie) {
    movie.overview?.let { overview ->
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = overview,
            color = AppTheme.colors.secondaryText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CategoryText(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CategoryTitleText(title)
        CategoryValueText(value)
    }
}

@Composable
private fun CategoryTitleText(title: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = title,
        color = AppTheme.colors.strongText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun CategoryValueText(title: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = title,
        color = AppTheme.colors.secondaryText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

