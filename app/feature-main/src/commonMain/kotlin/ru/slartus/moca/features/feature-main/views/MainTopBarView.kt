package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppActionIcon
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.ScreenWidth


@Composable
internal fun MainTopBarView(
    modifier: Modifier = Modifier,
    screenWidth: ScreenWidth,
    title: String,
    onMenuClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
) {
    val strings = LocalAppStrings.current
    val navigationIcon: @Composable (() -> Unit)? = when (screenWidth) {
        ScreenWidth.Medium, ScreenWidth.Small -> { ->
            AppNavigationIcon(
                imageVector = Icons.Default.Menu,
                contentDescription = strings.menu
            ) {
                onMenuClick()
            }
        }
        ScreenWidth.Large -> null
    }

    TopBarView(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = {
            AppActionIcon(
                imageVector = Icons.Default.Search,
                contentDescription = strings.search
            ) {
                onSearchClick()
            }
            AppActionIcon(
                imageVector = Icons.Default.Refresh,
                contentDescription = strings.refresh
            ) {
                onRefreshClick()
            }
        }
    )
}
