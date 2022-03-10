package ru.slartus.moca.`core-ui`.views

import AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.slartus.moca.`core-ui`.modifiers.appClickable
import ru.slartus.moca.core_ui.theme.AppTheme

const val posterHeightWeightOfWidth = 1.4

@Composable
fun VideoCardView(
    modifier: Modifier = Modifier,
    card: VideoCard,
    cellWidth: Dp,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .fillMaxWidth()
            .appClickable {
                onClick.invoke()
            },
        backgroundColor = AppTheme.colors.secondaryBackground,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((cellWidth.value * posterHeightWeightOfWidth).dp)
            ) {
                if (card.posterUrl != null)
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        imageUrl = card.posterUrl,
                        contentDescription = card.title,
                        contentScale = ContentScale.FillWidth
                    )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(start = 4.dp, top = 4.dp, end = 4.dp),
                maxLines = 2,
                text = card.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.primaryText,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class VideoCard(
    val id: String,
    val title: String,
    val posterUrl: String?
)