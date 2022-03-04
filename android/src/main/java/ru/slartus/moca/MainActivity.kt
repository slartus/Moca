package ru.slartus.moca

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import appProviders
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import navigation.generateGraph
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetNavigator
import ru.slartus.moca.core_ui.theme.AppTheme
import withApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootController = RootComposeBuilder().apply { generateGraph() }.build()
        rootController.setupWithActivity(this)
        setContent {
            val providers = arrayOf(
                *appProviders(),
                LocalRootController provides rootController
            )
            CompositionLocalProvider(
                *providers
            ) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !AppTheme.colors.darkTheme
                val statusBarColor = AppTheme.colors.actionBarColor
                val navigationBarColor = AppTheme.colors.navigationBarColor

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = statusBarColor,
                        darkIcons = useDarkIcons
                    )
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor,
                        darkIcons = useDarkIcons
                    )
                }
                withApp {
                    ModalSheetNavigator {
                        Navigator("main")
                    }
                }
            }
        }
    }
}