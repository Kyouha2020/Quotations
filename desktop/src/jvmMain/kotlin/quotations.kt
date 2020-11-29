import androidx.compose.desktop.Window
import data.QuotationOwner
import ui.Quotations

fun quotations(owner: QuotationOwner) = Window(
    title = "Quotations",
    icon = getMyAppIcon()
) {
    QuotationsTheme {
        Quotations(owner)
    }
}