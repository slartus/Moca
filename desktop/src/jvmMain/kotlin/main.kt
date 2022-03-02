import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.therabody.plus.core_lib.ui.compose.themes.LocalAppColors
import com.therabody.plus.core_lib.ui.compose.themes.darkPalette
import com.therabody.plus.core_lib.ui.compose.themes.lightPalette
import navigation.generateGraph
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.slartus.moca.`core-ui`.theme.Language
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.theme.enStrings
import ru.slartus.moca.`core-ui`.theme.ruStrings
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    val darkTheme = true
    val language = Language.Ru

    val platformSettings = PlatformSettings(getPlatform())

    val colors = if (darkTheme) darkPalette else lightPalette
    val strings = when (language) {
        Language.Ru -> ruStrings
        Language.En -> enStrings
    }

    window.setupNavigation(
        "test",
        LocalAppColors provides colors,
        LocalAppStrings provides strings,
        LocalPlatformSettings provides platformSettings
    ) {
        generateGraph()
    }
}