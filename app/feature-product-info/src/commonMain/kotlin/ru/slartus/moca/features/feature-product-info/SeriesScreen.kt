package ru.slartus.moca.features.`feature-product-info`

import PlatformListener
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberFactory
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.features.`feature-product-info`.views.*

@Composable
fun SeriesScreen(series: Series) {
    val viewModelFactory by rememberFactory<Series, SeriesScreenViewModel>()
    val viewModel by remember(series) { mutableStateOf(viewModelFactory(series)) }
    val viewState by viewModel.stateFlow.collectAsState()
    val actions by viewModel.actionsFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val strings = LocalAppStrings.current
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )

    actions.firstOrNull()?.let {
        viewModel.actionReceived(it.id)
        when (it) {
            is SeriesAction.Error ->
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.background,
        topBar = { TopBar(series.title) }
    ) {
        var tabIndex by remember { mutableStateOf(0) }
        val tabTitles by remember(viewState.hasTorrentsSources) {
            val titles = (listOf(strings.description) +
                if (viewState.hasTorrentsSources) listOf(strings.torrents) else emptyList()
                )
                .map { it.uppercase() }
            mutableStateOf(titles)
        }
        Column {
            TabRow(
                backgroundColor = AppTheme.colors.primary,
                contentColor = AppTheme.colors.secondaryVariant,
                selectedTabIndex = tabIndex
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
            when (tabIndex) {
                0 -> InfoView(viewState = viewState)
                1 -> TorrentsListView(series) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoView(modifier: Modifier = Modifier, viewState: SeriesViewState) {
    val platformListener by rememberInstance<PlatformListener>()
    Box(modifier = modifier.fillMaxSize()) {
        val movieDetails = viewState.data
        LazyColumn(modifier = modifier.padding(10.dp).fillMaxSize()) {
            item {
                Box(modifier = Modifier.clickable {
                    movieDetails.videos.firstOrNull()?.let {
                        platformListener.openUrl(it)
                    }
                }) {
                    Title(movieDetails.title)
                }
            }
            item { OriginalTitle(movieDetails.originalTitle) }
            item { PosterView(movieDetails.posterUrl, movieDetails.year, movieDetails.rates) }
            item { Description(movieDetails.overview) }
        }
        if (viewState.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AppTheme.colors.highLight,
            )
    }
}
