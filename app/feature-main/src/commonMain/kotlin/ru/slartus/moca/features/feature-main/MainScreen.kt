package ru.slartus.moca.features.`feature-main`

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.slartus.moca.core.AppScreenName
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.core_ui.screenWidth
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.ProductType
import ru.slartus.moca.features.`feature-main`.videoGridViews.MoviesView
import ru.slartus.moca.features.`feature-main`.videoGridViews.SeriesView
import ru.slartus.moca.features.`feature-main`.views.DrawerView
import ru.slartus.moca.features.`feature-main`.views.MainTopBarView
import ru.slartus.moca.features.`feature-main`.views.customDrawerShape

@Composable
fun MainScreen() {
    val rootController = LocalRootController.current
    val coroutineScope = rememberCoroutineScope()
    val screenViewModel: MainScreenViewModel by rememberInstance()
    val viewState by screenViewModel.stateFlow.collectAsState()

    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed) { drawerValue ->
            if (drawerValue == DrawerValue.Closed)
                screenViewModel.onEvent(Event.OnDrawerClosed)
            true
        },
        snackbarHostState = remember { SnackbarHostState() }
    )
    if (scaffoldState.drawerState.isOpen != viewState.drawerOpened) {
        coroutineScope.launch {
            if (viewState.drawerOpened) {
                scaffoldState.drawerState.open()
            } else {
                scaffoldState.drawerState.close()
            }
        }
    }
    var refresh = false
    viewState.actions.firstOrNull()?.let {
        screenViewModel.actionReceived(it.id)
        when (it) {
            is Action.Error -> coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(it.message)
            }
            is Action.Refresh -> refresh = true
            Action.OpenSearchScreen -> rootController.launch(
                AppScreenName.Search.name,
                params = viewState.subScreen,
                animationType = AnimationType.Present(300)
            )
            Action.OpenSettingsScreen -> rootController.launch(
                AppScreenName.Settings.name,
                animationType = AnimationType.Present(300)
            )
        }
    }
    BoxWithConstraints {
        val screenWidth = maxWidth.screenWidth
        val drawerContent: @Composable (ColumnScope.() -> Unit)? = when (screenWidth) {
            ScreenWidth.Medium, ScreenWidth.Small -> { ->
                DrawerView(
                    modifier = Modifier,
                    eventListener = screenViewModel
                )
            }
            ScreenWidth.Large -> null
        }
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppTheme.colors.primaryBackground,
            topBar = {
                MainTopBarView(
                    title = viewState.title,
                    screenWidth = screenWidth,
                    onMenuClick = { screenViewModel.onEvent(Event.MenuClick) },
                    onRefreshClick = { screenViewModel.onEvent(Event.RefreshClick) },
                    onSearchClick = { screenViewModel.onEvent(Event.SearchClick) },
                )
            },
            drawerShape = customDrawerShape(250.dp),
            drawerContent = drawerContent
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (screenWidth == ScreenWidth.Large) {
                    DrawerView(modifier = Modifier.width(200.dp), eventListener = screenViewModel)
                }
                SubScreenView(viewState.subScreen, screenViewModel, refresh)
            }
        }
    }
}

@Composable
private fun SubScreenView(subScreen: ProductType, eventListener: EventListener, refresh: Boolean) {
    val rootController = LocalRootController.current

    when (subScreen) {
        ProductType.Movie -> MoviesView(
            tag = "movies",
            refresh = refresh,
            onItemClick = { item ->
                rootController.launch(
                    AppScreenName.MovieInfo.name,
                    params = item,
                    animationType = AnimationType.Present(300)
                )
            },
            onError = {
                eventListener.onEvent(Event.Error(it))
            }
        )
        ProductType.Series -> SeriesView(
            tag = "series",
            refresh = refresh,
            onItemClick = { item ->
                rootController.launch(
                    AppScreenName.SeriesInfo.name,
                    params = item,
                    animationType = AnimationType.Present(300)
                )
            },
            onError = { eventListener.onEvent(Event.Error(it)) }
        )
        ProductType.AnimationMovie -> MoviesView(
            tag = "animation.movies",
            refresh = refresh,
            onItemClick = { item ->
                rootController.launch(
                    AppScreenName.MovieInfo.name,
                    params = item,
                    animationType = AnimationType.Present(300)
                )
            },
            onError = {
                eventListener.onEvent(Event.Error(it))
            }
        )
        ProductType.AnimationSeries -> SeriesView(
            tag = "animation.series",
            refresh = refresh,
            onItemClick = { item ->
                rootController.launch(
                    AppScreenName.SeriesInfo.name,
                    params = item,
                    animationType = AnimationType.Present(300)
                )
            },
            onError = { eventListener.onEvent(Event.Error(it)) }
        )
    }

}