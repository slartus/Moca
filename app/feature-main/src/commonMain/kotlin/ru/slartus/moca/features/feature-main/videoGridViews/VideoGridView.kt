package ru.slartus.moca.features.`feature-main`.videoGridViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.slartus.moca.`core-ui`.views.VideoCard
import ru.slartus.moca.`core-ui`.views.VideoCardView

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun VideoGridView(
    modifier: Modifier = Modifier,
    data: List<VideoCard>,
    onCardClick: (card: VideoCard) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        cells = GridCells.Adaptive(128.dp),
    ) {
        data.forEach { card ->
            item {
                VideoCardView(modifier = Modifier, card) {
                    onCardClick(card)
                }
            }
        }
    }
}