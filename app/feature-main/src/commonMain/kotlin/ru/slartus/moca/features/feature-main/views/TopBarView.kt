package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.core_ui.theme.AppTheme


@Composable
internal fun TopBarView(
    modifier: Modifier = Modifier,
    screenWidth: ScreenWidth,
    title: String,
    onMenuClick: () -> Unit = {}
) {
    val iconContent: @Composable (() -> Unit)? = when (screenWidth) {
        ScreenWidth.Medium, ScreenWidth.Small -> { ->
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = onMenuClick),
                tint = AppTheme.colors.actionBarContentColor
            )
        }
        ScreenWidth.Large -> null
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, color = AppTheme.colors.actionBarContentColor)
        },
        navigationIcon = iconContent,
        backgroundColor = AppTheme.colors.primarySurface,
        contentColor = AppTheme.colors.actionBarContentColor
    )
}