package ru.slartus.moca.features.`feature-main`

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.features.`feature-main`.views.TopBarView

@Composable
fun MovieScreen() {
    TopBarView(title="Movie", screenWidth = ScreenWidth.Small)
}