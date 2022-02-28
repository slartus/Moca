package ru.slartus.moca.features.`feature-main`

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.core_ui.screenWidth
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.features.`feature-main`.views.DrawerView
import ru.slartus.moca.features.`feature-main`.views.TopBarView
import ru.slartus.moca.features.`feature-main`.views.customDrawerShape
import ru.slartus.moca.features.feature_popular.PopularView

@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Open),
        snackbarHostState = snackbarHostState
    )
    val coroutineScope = rememberCoroutineScope()
    val eventListener: EventListener = object : EventListener {
        override fun onEvent(event: Event) {
            when (event) {
                Event.MenuMoviesClick -> {

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
                    onMenuClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            drawerShape = customDrawerShape(250.dp),
            drawerContent = drawerContent
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (screenWidth == ScreenWidth.Large) {
                    DrawerView(modifier = Modifier.width(200.dp), eventListener = eventListener)
                }
                PopularView(
                    onError = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = it.message ?: it.toString()
                            )
                        }
                    }
                )
            }

        }
    }
}
