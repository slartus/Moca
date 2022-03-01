package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.features.`feature-main`.Event
import ru.slartus.moca.features.`feature-main`.EventListener

@Composable
internal fun DrawerView(modifier: Modifier = Modifier, eventListener: EventListener) {
    Column(
        modifier = modifier
            .background(AppTheme.colors.drawerColor)
            .fillMaxSize()
    ) {
        DrawerMenuItem(title = "Фильмы") {
            eventListener.onEvent(Event.MenuMoviesClick)
        }
        DrawerMenuItem(title = "Сериалы") {
            eventListener.onEvent(Event.MenuTvClick)
        }
    }
}