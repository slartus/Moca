package ru.slartus.moca.features.`feature-main`.videoGridViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val state: LazyListState = rememberLazyListState()
    var dataSize by remember { mutableStateOf(0) }



    LaunchedEffect(dataSize) {
        if (dataSize > 0) {
            onLoadMoreEvent()
        }
    }
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val cellsCount = maxOf((maxWidth / 128.dp).toInt(), 1)
        getLoadMoreKey(data, state.layoutInfo, cellsCount)?.let {
            dataSize = it
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

private fun getLoadMoreKey(
    data: List<Any>,
    listLayoutInfo: LazyListLayoutInfo,
    columnsCount: Int
): Int? {
    if (data.isNotEmpty()) {
        val lastItemIndex = listLayoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val screenCount = listLayoutInfo.totalItemsCount

        val needLoadMore =
            (lastItemIndex + 1) * columnsCount > data.size - screenCount
        if (needLoadMore) {
            return data.size
        }

    }
    return null
}