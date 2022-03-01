package ru.slartus.moca

import App
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import getPlatform
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val platformSettings = PlatformSettings(getPlatform())
            AppTheme(platformSettings = platformSettings, darkTheme = true) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !AppTheme.colors.darkTheme
                val statusBarColor = AppTheme.colors.statusBarColor
                val navigationBarColor = AppTheme.colors.navigationBarColor
                SideEffect {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = statusBarColor,
                        darkIcons = useDarkIcons
                    )
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor,
                        darkIcons = useDarkIcons
                    )
                }

                App()
            }
        }
    }
}