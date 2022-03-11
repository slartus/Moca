package ru.slartus.moca.features.`feature-product-info`

import PlatformListener
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icPlay
import org.kodein.di.compose.rememberFactory
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent


@Composable
fun <T : Product> TorrentsListView(product: T, onError: (message: String) -> Unit) {
    val viewModelFactory by rememberFactory<Product, TorrentsListViewModel>()
    val viewModel by remember(product) { mutableStateOf(viewModelFactory(product)) }
    val viewState by viewModel.stateFlow.collectAsState()
    val platformListener by rememberInstance<PlatformListener>()

    viewState.actions.firstOrNull()?.let {
        viewModel.actionReceived(it.id)
        when (it) {
            is TorrentsAction.Error -> onError(it.message)
            is TorrentsAction.OpenFile -> platformListener.openFile(it.appFile)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (viewState.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.TopEnd).size(15.dp),
                color = AppTheme.colors.highLight,
                strokeWidth = 1.dp
            )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            viewState.data.forEach { torrent ->
                item {
                    Column {
                        TorrentView(torrent) {
                            viewModel.onTorrentClick(it)
                        }
                        Box(
                            modifier = Modifier.height(1.dp).fillMaxWidth()
                                .background(color = AppTheme.colors.secondaryText)

                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TorrentView(torrent: Torrent, onClick: (torrent: Torrent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(torrent)
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

                val peersDown = torrent.peers
                val peersUp = torrent.seeds
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