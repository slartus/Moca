package ru.slartus.moca.features.feature_main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
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
            drawerShape = customDrawerShape(250.dp),
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

/**
 * !TODO: клик обрабатывает по старому размеру (на десктопе норм)
 * @see androidx.compose.foundation.shape.RoundedCornerShape
 * @see androidx.compose.foundation.shape.CornerBasedShape
 */
private fun customDrawerShape(width: Dp) = object : Shape {
    val topStart: CornerSize = CornerSize(4.dp)
    val topEnd: CornerSize = CornerSize(4.dp)
    val bottomEnd: CornerSize = CornerSize(4.dp)
    val bottomStart: CornerSize = CornerSize(4.dp)
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val widthPx = with(density) { width.toPx() }
        val newSize = size.copy(width = widthPx)
        var topStart = topStart.toPx(newSize, density)
        var topEnd = topEnd.toPx(newSize, density)
        var bottomEnd = bottomEnd.toPx(newSize, density)
        var bottomStart = bottomStart.toPx(newSize, density)
        val minDimension = newSize.minDimension
        if (topStart + bottomStart > minDimension) {
            val scale = minDimension / (topStart + bottomStart)
            topStart *= scale
            bottomStart *= scale
        }
        if (topEnd + bottomEnd > minDimension) {
            val scale = minDimension / (topEnd + bottomEnd)
            topEnd *= scale
            bottomEnd *= scale
        }
        require(topStart >= 0.0f && topEnd >= 0.0f && bottomEnd >= 0.0f && bottomStart >= 0.0f) {
            "Corner size in Px can't be negative(topStart = $topStart, topEnd = $topEnd, " +
                "bottomEnd = $bottomEnd, bottomStart = $bottomStart)!"
        }
        return if (topStart + topEnd + bottomEnd + bottomStart == 0.0f) {
            Outline.Rectangle(newSize.toRect())
        } else {
            Outline.Rounded(
                RoundRect(
                    rect = newSize.toRect(),
                    topLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd),
                    topRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart),
                    bottomRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart),
                    bottomLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd)
                )
            )
        }
    }
}