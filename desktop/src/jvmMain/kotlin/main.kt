import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        this.window.title = "Moca"
        val platformSettings = PlatformSettings(getPlatform())
        AppTheme(platformSettings = platformSettings, darkTheme = true) {
            App()
        }
    }
}