package ru.slartus.moca.features.`feature-product-info`

import PlatformListener
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icPlay
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.domain.models.Product


@Composable
internal fun <T : Product> TorrentsListView(
    product: T,
    onError: (message: String) -> Unit
) {
    val viewModel by rememberInstance<TorrentsListViewModel>()
    remember(product) {
        viewModel.setProduct(product)
    }
    val viewState by viewModel.stateFlow.collectAsState()
    val actions by viewModel.actionsFlow.collectAsState()
    val platformListener by rememberInstance<PlatformListener>()

    actions.firstOrNull()?.let {
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewState.data) { torrent ->
                Column {
                    TorrentView(torrent) {
                        viewModel.onTorrentClick(torrent)
                    }
                }
            }
        }
    }
}

@Composable
private fun TorrentView(torrent: TorrentItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.primary)
            .clickable {
                if (!torrent.isLoading)
                    onClick()
            }
            .padding(start = 10.dp)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(3.dp).align(CenterVertically)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                painter = icPlay(),
                contentDescription = "play",
                tint = AppTheme.colors.primaryVariant
            )
            if (torrent.isLoading)
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).size(25.dp),
                    color = AppTheme.colors.highLight,
                    strokeWidth = 2.dp
                )
        }

        Column(modifier = Modifier.padding(start = 10.dp).fillMaxSize()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = torrent.title,
                fontSize = 16.sp,
                color = AppTheme.colors.secondaryVariant
            )
            torrent.size?.let { size ->
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 1.dp),
                    text = size,
                    fontSize = 14.sp,
                    color = AppTheme.colors.secondaryVariant
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
                    color = AppTheme.colors.secondaryVariant,

                    )

                val peersDown = torrent.peers
                val peersUp = torrent.seeds
                if (peersDown != null && peersUp != null) {
                    Text(
                        modifier = Modifier,
                        text = "$peersUp/$peersDown",
                        fontSize = 12.sp,
                        color = AppTheme.colors.secondaryVariant
                    )
                }
            }
        }
    }
}