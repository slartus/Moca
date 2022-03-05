package ru.slartus.moca.features.`feature-main`.videoCardScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icPlay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.domain.repositories.TorrentsRepository


@Composable
fun TorrentsListView(title: String, originalTitle: String) {
    val repository: TorrentsRepository by rememberInstance()
    val coroutineScope = rememberCoroutineScope()

    var torrents: List<Torrent> by remember { mutableStateOf(emptyList()) }

    LazyColumn {
        torrents.forEach { torrent ->
            item {
                Column {
                    TorrentView(torrent)
                    Box(
                        modifier=Modifier.height(0.5.dp).fillMaxWidth()
                            .background(color = AppTheme.colors.secondaryText)

                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            repository.items.collect {
                torrents = it
            }
        }
        coroutineScope.launch {
            repository.find(title)
        }
    }

}

@Composable
private fun TorrentView(torrent: Torrent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }
            .padding(start = 10.dp)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 5.dp)
                .size(50.dp)
                .padding(3.dp).align(CenterVertically)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                painter = icPlay(),
                contentDescription = "play",
                tint = AppTheme.colors.actionBarContentColor
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = torrent.title,
                fontSize = 16.sp,
                color = AppTheme.colors.primaryText
            )
            torrent.size?.let { size ->
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 1.dp),
                    text = size,
                    fontSize = 14.sp,
                    color = AppTheme.colors.primaryText
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = torrent.source,
                    fontSize = 12.sp,
                    color = AppTheme.colors.secondaryText,

                    )

                val peersDown = torrent.peersDown
                val peersUp = torrent.peersUp
                if (peersDown != null && peersUp != null) {
                    Text(
                        modifier = Modifier,
                        text = "$peersUp/$peersDown",
                        fontSize = 12.sp,
                        color = AppTheme.colors.secondaryText
                    )
                }
            }
        }
    }
}