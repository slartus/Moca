package ru.slartus.moca.features.`feature-main`.videoGridViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
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
    onCardClick: (card: VideoCard) -> Unit,
    onLoadMoreEvent: () -> Unit = {}
) {
    var dataSize by remember { mutableStateOf(0) }

    remember(dataSize) {
        if (dataSize > 0) {
            Napier.d("onLoadMoreEvent $dataSize")
            onLoadMoreEvent()
        }
    }
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val state: LazyListState = rememberLazyListState()
        val cellsCount = maxOf((maxWidth / 128.dp).toInt(), 1)
        val lastItemIndex = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val screenCount = state.layoutInfo.totalItemsCount
        val scrollInfo = ScrollInfo(data.size, lastItemIndex, screenCount, cellsCount)
        remember(scrollInfo) {
            dataSize = if (scrollInfo.needLoadMore) data.size else 0
        }

        LazyVerticalGrid(
            state = state,
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Fixed(cellsCount),
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
}

private data class ScrollInfo(
    val dataSize: Int,
    val lastItemIndex: Int,
    val screenCount: Int,
    val columnsCount: Int
) {
    val needLoadMore = dataSize > 0 && (lastItemIndex + 1) * columnsCount > dataSize - screenCount
}