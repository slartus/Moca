import navigation.generateGraph
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    window.setupNavigation(
        "test",
        *appProviders()
    ) {
        generateGraph()
    }
}