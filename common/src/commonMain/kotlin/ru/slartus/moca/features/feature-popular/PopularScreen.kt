package ru.slartus.moca.features.feature_popular

import AsyncImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.CatalogRepository

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularScreen() {
    var popular: List<Movie> by mutableStateOf(emptyList())

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Adaptive(128.dp),
    ) {
        popular.forEach { movie ->
            item {
                MovieView(modifier = Modifier, movie)
            }
        }
    }

    val catalogRepository: CatalogRepository by rememberInstance()
    LaunchedEffect(key1 = Unit, block = {
        popular = catalogRepository.getPopularMovies()
    })
}

@Composable
private fun MovieView(modifier: Modifier = Modifier, movie: Movie) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 5.dp, vertical = 5.dp),
        backgroundColor = AppTheme.colors.secondaryBackground,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = 1f),
                imageUrl = movie.posterUrl!!,
                contentDescription = movie.title,
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(start = 4.dp, top = 4.dp, end = 4.dp),
                maxLines = 2,
                text = movie.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.primaryText,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}