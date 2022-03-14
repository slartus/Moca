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
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.features.`feature-main`.Event
import ru.slartus.moca.features.`feature-main`.EventListener

@Composable
internal fun DrawerView(modifier: Modifier = Modifier, eventListener: EventListener) {
    val strings = LocalAppStrings.current
    Column(
        modifier = modifier
            .background(AppTheme.colors.surface)
            .fillMaxSize()
    ) {
        DrawerMenuItem(title = strings.movies, icon = icMovies()) {
            eventListener.obtainEvent(Event.MenuMoviesClick)
        }
        DrawerMenuItem(title = strings.series, icon = icTv()) {
            eventListener.obtainEvent(Event.MenuTvClick)
        }
        DrawerMenuItem(title = strings.animationMovies, icon = icCartoonMovies()) {
            eventListener.obtainEvent(Event.MenuAnimationMoviesClick)
        }
        DrawerMenuItem(title = strings.animationSeries, icon = icCartoonTv()) {
            eventListener.obtainEvent(Event.MenuAnimationTvClick)
        }

        Spacer(modifier = Modifier.weight(1f))
        DrawerMenuItem(title = strings.settings, icon = icSettings()) {
            eventListener.obtainEvent(Event.MenuSettingsClick)
        }
    }
}