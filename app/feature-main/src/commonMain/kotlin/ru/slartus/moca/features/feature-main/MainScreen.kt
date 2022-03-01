package ru.slartus.moca.features.`feature-main`

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.core_ui.screenWidth
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.features.`feature-main`.views.DrawerView
import ru.slartus.moca.features.`feature-main`.views.TopBarView
import ru.slartus.moca.features.`feature-main`.views.customDrawerShape
import ru.slartus.moca.features.feature_popular.PopularMoviesView
import ru.slartus.moca.features.feature_popular.PopularTvView

@Composable
fun MainScreen() {
    var viewState: ScreenState by remember {
        mutableStateOf(
            ScreenState(
                subScreen = SubScreen.Movies,
                error = null,
                drawerOpened = false
            )
        )
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        snackbarHostState = snackbarHostState
    )
    val coroutineScope = rememberCoroutineScope()
    val eventListener: EventListener = object : EventListener {
        override fun onEvent(event: Event) {
            when (event) {
                is Event.Error -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.error.message ?: event.error.toString()
                        )
                    }
                }
                else -> {
                    viewState = reduceState(screenState = viewState, event)
                    coroutineScope.launch {
                        if (viewState.drawerOpened) {
                            scaffoldState.drawerState.open()
                        } else {
                            scaffoldState.drawerState.close()
                        }
                    }
                }
            }
        }
    }
    BoxWithConstraints {
        val screenWidth = maxWidth.screenWidth

        val drawerContent: @Composable (ColumnScope.() -> Unit)? = when (screenWidth) {
            ScreenWidth.Medium, ScreenWidth.Small -> { ->
                DrawerView(
                    modifier = Modifier,
                    eventListener = eventListener
                )
            }
            ScreenWidth.Large -> null
        }
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppTheme.colors.primaryBackground,
            topBar = {
                TopBarView(
                    screenWidth = screenWidth,
                    onMenuClick = { eventListener.onEvent(Event.MenuClick) }
                )
            },
            drawerShape = customDrawerShape(250.dp),
            drawerContent = drawerContent
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (screenWidth == ScreenWidth.Large) {
                    DrawerView(modifier = Modifier.width(200.dp), eventListener = eventListener)
                }
                SubScreenView(viewState.subScreen, eventListener)
            }
        }
    }
}

private fun reduceState(screenState: ScreenState, event: Event): ScreenState {
    return when (event) {
        Event.MenuMoviesClick -> {
            ScreenState(
                subScreen = SubScreen.Movies,
                error = null,
                drawerOpened = false
            )
        }
        Event.MenuTvClick -> {
            ScreenState(
                subScreen = SubScreen.Tv,
                error = null,
                drawerOpened = false
            )
        }
        is Event.Error -> {
            error("error reduce state: $event")
        }
        Event.MenuClick -> {
            ScreenState(
                subScreen = screenState.subScreen,
                error = screenState.error,
                drawerOpened = !screenState.drawerOpened
            )
        }
    }
}

@Composable
private fun SubScreenView(subScreen: SubScreen, eventListener: EventListener) {
    when (subScreen) {
        SubScreen.Movies -> PopularMoviesView(
            onError = { eventListener.onEvent(Event.Error(it)) }
        )
        SubScreen.Tv -> PopularTvView(
            onError = { eventListener.onEvent(Event.Error(it)) }
        )
    }
}


private data class ScreenState(
    val subScreen: SubScreen,
    val error: Exception?,
    val drawerOpened: Boolean
)

private enum class SubScreen {
    Movies, Tv
}