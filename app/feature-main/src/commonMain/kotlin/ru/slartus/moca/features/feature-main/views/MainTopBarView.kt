package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.ScreenWidth


@Composable
internal fun MainTopBarView(
    modifier: Modifier = Modifier,
    screenWidth: ScreenWidth,
    title: String,
    onMenuClick: () -> Unit = {}
) {
    val navigationIcon: @Composable (() -> Unit)? = when (screenWidth) {
        ScreenWidth.Medium, ScreenWidth.Small -> { ->
            AppNavigationIcon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            ) {
                onMenuClick()
            }
        }
        ScreenWidth.Large -> null
    }

    TopBarView(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon
    )
}
