package ru.slartus.moca

import App
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.therabody.plus.core_lib.ui.compose.themes.LocalAppColors
import com.therabody.plus.core_lib.ui.compose.themes.darkPalette
import com.therabody.plus.core_lib.ui.compose.themes.lightPalette
import getPlatform
import navigation.generateGraph
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetNavigator
import ru.slartus.moca.`core-ui`.theme.Language
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.theme.enStrings
import ru.slartus.moca.`core-ui`.theme.ruStrings
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme
import withDI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val darkTheme = true
        val language = Language.Ru

        val rootController = RootComposeBuilder().apply { generateGraph() }.build()
        rootController.setupWithActivity(this)
        val platformSettings = PlatformSettings(getPlatform())
        setContent {

            val colors = if (darkTheme) darkPalette else lightPalette
            val strings = when (language) {
                Language.Ru -> ruStrings
                Language.En -> enStrings
            }

            val providers = arrayOf(
                LocalAppColors provides colors,
                LocalAppStrings provides strings,
                LocalPlatformSettings provides platformSettings,
                LocalRootController provides rootController
            )
            CompositionLocalProvider(
                *providers
            ) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !AppTheme.colors.darkTheme
                val statusBarColor = AppTheme.colors.statusBarColor
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
                withDI {
                    ModalSheetNavigator {
                        Navigator("main")
                    }
                }
            }
        }
    }
}