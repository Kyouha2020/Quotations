import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import data.Quotation
import data.QuotationBlock

expect fun getPlatformName(): String
expect fun isDarkTheme(): Boolean
expect fun parseQuotationBlocks(): List<QuotationBlock>
expect fun parseQuotations(url: String): List<Quotation>

@Composable
fun QuotationsTheme(darkTheme: Boolean = isDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) darkColors(
            primary = Color(0xFF8AB4F8),
            primaryVariant = Color(0xFF4285F4),
            secondary = Color(0xFF3DDC84)
        ) else lightColors(
            primary = Color(0xFF4285F4),
            primaryVariant = Color(0xFF4285F4),
            secondary = Color(0xFF3DDC84),
            secondaryVariant = Color(0xFF3DDC84)
        ),
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}