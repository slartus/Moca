import java.awt.Desktop
import java.net.URI
import java.net.URISyntaxException


actual class PlatformListener {
    actual fun openUrl(url: String) {
        val desktop: Desktop = java.awt.Desktop.getDesktop()
        try {
            val oURL = URI(url)
            desktop.browse(oURL)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

    }
}