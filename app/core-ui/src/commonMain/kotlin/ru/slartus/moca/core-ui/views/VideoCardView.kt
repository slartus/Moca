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
import androidx.compose.ui.unit.dp
import ru.slartus.moca.`core-ui`.modifiers.appClickable
import ru.slartus.moca.core_ui.theme.AppTheme

@Composable
fun VideoCardView(modifier: Modifier = Modifier, tv: VideoCard, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .appClickable {
                onClick.invoke()
            },
        backgroundColor = AppTheme.colors.secondaryBackground,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (tv.posterUrl != null)
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f),
                    imageUrl = tv.posterUrl,
                    contentDescription = tv.title,
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
                text = tv.title,
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