import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme
import java.awt.Dimension

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        this.window.title = "Moca"
        this.window.size = Dimension(1024, 840)
        val platformSettings = PlatformSettings(getPlatform())
        AppTheme(platformSettings = platformSettings, darkTheme = true) {
            App()
        }
    }
}