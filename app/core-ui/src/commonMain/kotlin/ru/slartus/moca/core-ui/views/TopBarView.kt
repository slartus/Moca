package ru.slartus.moca.`core-ui`.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.slartus.moca.core_ui.theme.AppTheme

@Composable
fun TopBarView(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = AppTheme.colors.actionBarColor,
    contentColor: Color = AppTheme.colors.actionBarContentColor,
    elevation: Dp = 0.dp

) {
    val titleView: @Composable (() -> Unit) = {
        Text(text = title, color = AppTheme.colors.actionBarContentColor)
    }

    TopBarView(
        modifier = modifier,
        title = titleView,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}

@Composable
fun AppNavigationIcon(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.size(48.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.actionBarContentColor
        )
    }
}

@Composable
fun AppActionIcon(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.size(48.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.actionBarContentColor
        )
    }
}

@Composable
fun TopBarView(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = AppTheme.colors.primarySurface,
    contentColor: Color = AppTheme.colors.actionBarContentColor,
    elevation: Dp = AppBarDefaults.TopAppBarElevation

) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}
