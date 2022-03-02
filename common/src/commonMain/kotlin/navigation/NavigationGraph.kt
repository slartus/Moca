package navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.generateGraph() {
    screen(name = "main") {
        MainScreen()
    }
    screen(name = "movie") {
        MovieScreen()
    }
}

@Composable
fun MainScreen() {
    val rootController = LocalRootController.current
    Box() {
        Text(text = "Hello Odyssey",
            modifier = Modifier.clickable {
                rootController.launch("movie")
            }
        )
    }
}

@Composable
fun MovieScreen() {

    Box() {
        Text(text = "Movie"
        )
    }
}