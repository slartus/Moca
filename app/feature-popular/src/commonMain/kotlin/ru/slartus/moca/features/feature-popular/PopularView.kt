package ru.slartus.moca.features.feature_popular

import AsyncImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.`core-ui`.appClickable
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.CatalogRepository

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularView(
    onError: (ex: Exception) -> Unit = {}
) {
    var viewState: ScreenState by remember {
        mutableStateOf(
            ScreenState(
                isLoading = true,
                data = emptyList(),
                error = null
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val listState = rememberLazyListState()
        LazyVerticalGrid(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Adaptive(128.dp),
        ) {
            viewState.data.forEach { movie ->
                item {
                    MovieView(modifier = Modifier, movie)
                }
            }
        }

        if (viewState.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AppTheme.colors.primary,
            )
        else if (viewState.data.isEmpty())
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No data",
                color = AppTheme.colors.primaryText
            )
        viewState.error?.let {
            onError(it)
        }
    }

    val catalogRepository: CatalogRepository by rememberInstance()
    LaunchedEffect(key1 = Unit, block = {
        viewState = try {
            val popular = catalogRepository.getPopularMovies()
            ScreenState(
                isLoading = false,
                data = popular,
                error = viewState.error
            )
        } catch (ex: Exception) {
            ScreenState(
                isLoading = false,
                data = viewState.data,
                error = ex
            )
        }
    })
}

@Composable
private fun MovieView(modifier: Modifier = Modifier, movie: Movie) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .appClickable {

            },
        backgroundColor = AppTheme.colors.secondaryBackground,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (movie.posterUrl != null)
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f),
                    imageUrl = movie.posterUrl!!,
                    contentDescription = movie.title,
                    contentScale = ContentScale.FillWidth
                )
            else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f)
                )
            }
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


data class ScreenState(
    val isLoading: Boolean,
    val data: List<Movie>,
    val error: Exception?
)