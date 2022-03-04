package ru.slartus.moca.features.`feature-main`

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.features.`feature-main`.views.*

@Composable
fun SeriesScreen(series: Series) {
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = { TopBar(series.title) }
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp).fillMaxSize()) {
            item { Title(series.title) }
            item { OriginalTitle(series.originalTitle) }
            item { PosterView(series.posterUrl, series.year, series.rates) }
            item { Description(series.overview) }
        }
    }
}

