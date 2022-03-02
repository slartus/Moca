package navigation

import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.slartus.moca.features.`feature-main`.MainScreen
import ru.slartus.moca.features.`feature-main`.MovieScreen


fun RootComposeBuilder.generateGraph() {
    screen(name = "main") {
        MainScreen()
    }
    screen(name = "movie") {
        MovieScreen()
    }
}
