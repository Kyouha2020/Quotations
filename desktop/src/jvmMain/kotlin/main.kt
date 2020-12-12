import androidx.compose.desktop.Window
import ui.Home
import java.awt.Color
import java.awt.image.BufferedImage

fun main() = Window(
    title = App.name,
    icon = getMyAppIcon()
) {
    QuotationsTheme {
        Home()
    }
}

fun getMyAppIcon(): BufferedImage {
    val size = 256
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    graphics.color = Color(0x4285F4)
    graphics.fillOval(0, 0, size, size)
    graphics.dispose()
    return image
}