package ru.slartus.moca.features.feature_popular

import AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.CatalogRepository

@Composable
fun PopularScreen() {
    var popular: List<Movie> by mutableStateOf(emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
            .padding(horizontal = 10.dp, vertical = 5.dp),
        elevation = 4.dp
    ) {
        Column {
            AsyncImage(movie.posterUrl!!)
        }
        Text(
            modifier = Modifier.padding(5.dp),
            text = movie.title
        )
    }
}