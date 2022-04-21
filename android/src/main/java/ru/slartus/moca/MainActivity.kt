package ru.slartus.moca

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import appProviders
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import navigation.generateGraph
import org.kodein.di.DI
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.di.androidModule
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
                AppTheme(darkTheme = true) {
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = AppTheme.colors.isLight
                    val statusBarColor = AppTheme.colors.primary
                    val navigationBarColor = AppTheme.colors.primary

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

                    val di = DI {
                        import(androidModule)
                    }

                    withApp(di) {
                        ModalNavigator {
                            Navigator()
                        }
                    }
                }
            }
        }
    }
}