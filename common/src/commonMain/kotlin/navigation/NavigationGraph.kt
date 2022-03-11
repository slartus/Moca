package navigation

import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.slartus.moca.core.AppScreenName
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.ProductType
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.features.`feature-main`.MainScreen
import ru.slartus.moca.features.`feature-product-info`.MovieScreen
import ru.slartus.moca.features.`feature-product-info`.SeriesScreen
import ru.slartus.moca.features.`feature-search`.SearchScreen
import ru.slartus.moca.features.`feature-settings`.SettingsScreen

fun RootComposeBuilder.generateGraph() {
    screen(name = AppScreenName.Main.name) {
        MainScreen()
    }
    screen(name = AppScreenName.Settings.name) {
        SettingsScreen()
    }


    screen(name = AppScreenName.Search.name) {
        SearchScreen(it as ProductType)
    }

    screen(name = AppScreenName.MovieInfo.name) {
        MovieScreen(it as Movie)
    }

    screen(name = AppScreenName.SeriesInfo.name) {
        SeriesScreen(it as Series)
    }

}
