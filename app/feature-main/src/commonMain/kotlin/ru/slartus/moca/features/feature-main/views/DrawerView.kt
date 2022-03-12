package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import icCartoonMovies
import icCartoonTv
import icMovies
import icSettings
import icTv
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.features.`feature-main`.Event
import ru.slartus.moca.features.`feature-main`.EventListener

@Composable
internal fun DrawerView(modifier: Modifier = Modifier, eventListener: EventListener) {
    val strings = LocalAppStrings.current
    Column(
        modifier = modifier
            .background(AppTheme.colors.drawerColor)
            .fillMaxSize()
    ) {
        DrawerMenuItem(title = strings.movies, icon = icMovies()) {
            eventListener.onEvent(Event.MenuMoviesClick)
        }
        DrawerMenuItem(title = strings.series, icon = icTv()) {
            eventListener.onEvent(Event.MenuTvClick)
        }
        DrawerMenuItem(title = strings.animationMovies, icon = icCartoonMovies()) {
            eventListener.onEvent(Event.MenuAnimationMoviesClick)
        }
        DrawerMenuItem(title = strings.animationSeries, icon = icCartoonTv()) {
            eventListener.onEvent(Event.MenuAnimationTvClick)
        }

        Spacer(modifier = Modifier.weight(1f))
        DrawerMenuItem(title = strings.settings, icon = icSettings()) {
            eventListener.onEvent(Event.MenuSettingsClick)
        }
    }
}