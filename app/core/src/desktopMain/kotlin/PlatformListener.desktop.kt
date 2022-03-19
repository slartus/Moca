import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.io.File
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

    actual fun openFile(appFile: AppFile) {
        try {
            val file = File(appFile.path)
            if (!Desktop.isDesktopSupported())
            {
                println("not supported")
                return
            }
            val desktop: Desktop = Desktop.getDesktop()
            if (file.exists()) //checks file exists or not
                desktop.open(file) //opens the specified file
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun copyToClipboard(text: String) {
        val stringSelectionObj = StringSelection(text)
        val clipboardObj: Clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
        clipboardObj.setContents(stringSelectionObj, null)
    }


}