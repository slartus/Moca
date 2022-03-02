package ru.slartus.moca.features.`feature-main`.videoGridViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import ru.slartus.moca.`core-ui`.views.VideoCard
import ru.slartus.moca.`core-ui`.views.VideoCardView

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun VideoGridView(
    modifier: Modifier = Modifier,
    data: List<VideoCard>,
    onCardClick: (card: VideoCard) -> Unit
) {
    val state: LazyListState = rememberLazyListState()
    SideEffect {
        Napier.e( state.layoutInfo.totalItemsCount.toString())
    }
    LazyVerticalGrid(
        state = state,
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