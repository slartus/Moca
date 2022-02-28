package ru.slartus.moca.features.feature_main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.slartus.moca.features.feature_popular.PopularScreen

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBarView(
                onMenuClick = {
                    coroutineScope.launch {

                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerShape = MaterialTheme.shapes.small,
        drawerContent = {
            DrawerView(
                modifier = Modifier
            )
        }
    ) {
        PopularScreen()
    }
}

@Composable
fun TopBarView(modifier: Modifier = Modifier, onMenuClick: () -> Unit = {}) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Scaffold||GFG", color = Color.White)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = onMenuClick),
                tint = Color.White
            )
        },
        backgroundColor = Color(0xFF0F9D58)
    )
}

@Composable
fun DrawerView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        repeat(5) { item ->
            Text(text = "Item number $item", modifier = Modifier.padding(8.dp), color = Color.Black)
        }
    }
}