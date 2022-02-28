package ru.slartus.moca.features.feature_main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.core_ui.screenWidth
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.features.feature_popular.PopularScreen

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints {
        val screenWidth = maxWidth.screenWidth

        val drawerContent: @Composable (ColumnScope.() -> Unit)? = when (screenWidth) {
            ScreenWidth.Medium, ScreenWidth.Small -> { ->
                DrawerView(
                    modifier = Modifier
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
            drawerShape = MaterialTheme.shapes.small,
            drawerContent = drawerContent
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (screenWidth == ScreenWidth.Large) {
                    DrawerView(modifier = Modifier.width(200.dp))
                }
                PopularScreen()
            }

        }
    }
}


@Composable
private fun TopBarView(
    modifier: Modifier = Modifier,
    screenWidth: ScreenWidth,
    onMenuClick: () -> Unit = {}
) {
    val iconContent: @Composable (() -> Unit)? = when (screenWidth) {
        ScreenWidth.Medium, ScreenWidth.Small -> { ->
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = onMenuClick),
                tint = Color.White
            )
        }
        ScreenWidth.Large -> null
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "MovieCatalog", color = AppTheme.colors.primaryText)
        },
        navigationIcon = iconContent,
        backgroundColor = AppTheme.colors.primarySurface,
        contentColor = AppTheme.colors.primaryText
    )
}


@Composable
private fun DrawerView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(AppTheme.colors.drawerColor)
            .fillMaxSize()
    ) {
        repeat(5) { item ->
            Text(text = "Item number $item", modifier = Modifier.padding(8.dp), color = Color.Black)
        }
    }
}