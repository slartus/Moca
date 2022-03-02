package ru.slartus.moca.features.`feature-main`

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.core_ui.screenWidth
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.features.`feature-main`.views.DrawerView

import ru.slartus.moca.features.`feature-main`.views.customDrawerShape
import ru.slartus.moca.features.`feature-main`.videoGridViews.PopularMoviesView
import ru.slartus.moca.features.`feature-main`.videoGridViews.PopularTvView
import ru.slartus.moca.features.`feature-main`.views.MainTopBarView

@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    val strings = AppTheme.strings
    val viewModel = remember { MainViewModel(strings, coroutineScope) }
    val viewState = viewModel.stateFlow.collectAsState()

    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed) { drawerValue ->
            if (drawerValue == DrawerValue.Closed)
                viewModel.onEvent(Event.OnDrawerClosed)
            true
        },
        snackbarHostState = remember { SnackbarHostState() }
    )
    if (scaffoldState.drawerState.isOpen != viewState.value.drawerOpened) {
        coroutineScope.launch {
            if (viewState.value.drawerOpened) {
                scaffoldState.drawerState.open()
            } else {
                scaffoldState.drawerState.close()
            }
        }
    }
    viewState.value.error?.let {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(it.message ?: it.toString())
        }
    }
    BoxWithConstraints {
        val screenWidth = maxWidth.screenWidth
        val drawerContent: @Composable (ColumnScope.() -> Unit)? = when (screenWidth) {
            ScreenWidth.Medium, ScreenWidth.Small -> { ->
                DrawerView(
                    modifier = Modifier,
                    eventListener = viewModel
                )
            }
            ScreenWidth.Large -> null
        }
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppTheme.colors.primaryBackground,
            topBar = {
                MainTopBarView(
                    title = viewState.value.title,
                    screenWidth = screenWidth,
                    onMenuClick = { viewModel.onEvent(Event.MenuClick) }
                )
            },
            drawerShape = customDrawerShape(250.dp),
            drawerContent = drawerContent
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (screenWidth == ScreenWidth.Large) {
                    DrawerView(modifier = Modifier.width(200.dp), eventListener = viewModel)
                }
                SubScreenView(viewState.value.subScreen, viewModel)
            }
        }
    }
}

@Composable
private fun SubScreenView(subScreen: SubScreen, eventListener: EventListener) {
    val rootController = LocalRootController.current
    when (subScreen) {
        SubScreen.Movies -> PopularMoviesView(
            onItemClick = { item ->
                rootController.launch(
                    "movie",
                    params = item,
                    animationType = AnimationType.Present(300)
                )
            },
            onError = { eventListener.onEvent(Event.Error(it)) }
        )
        SubScreen.Tv -> PopularTvView(
            onItemClick = { item ->
//                rootController.launch(
//                    "movie",
//                    params = item,
//                    animationType = AnimationType.Present(500)
//                )
            },
            onError = { eventListener.onEvent(Event.Error(it)) }
        )
    }
}