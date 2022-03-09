import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.awt.ComposePanel
import org.kodein.di.DI
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

fun JFrame.setupNavigation(
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()
    val composePanel = ComposePanel()

    // Below function setup drawing, you can extend it
    // by adding CompositionLocalProviders or something else
    composePanel.setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            val di = DI {
                import(desktopModule)
            }
            withApp(di) {
                Navigator()
            }
        }
    }

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    contentPane.add(composePanel, BorderLayout.CENTER)
    setLocationRelativeTo(null)
    isVisible = true
}