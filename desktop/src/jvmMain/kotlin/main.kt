import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import navigation.generateGraph
import java.awt.Color
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    Napier.base(DebugAntilog())
    val window = JFrame()
    window.title = "Moca"
    window.setSize(800, 600)
    window.background= Color.BLACK
    window.setupNavigation(
        *appProviders()
    ) {
        generateGraph()
    }
}