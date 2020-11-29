import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.beust.klaxon.Klaxon
import data.QuotationBlock
import java.net.URL

actual fun getPlatformName(): String = Platform.Android

@Composable
actual fun isDarkTheme(): Boolean = isSystemInDarkTheme()

actual fun fetchQuotationBlock(url: String): List<QuotationBlock>? {
    return Klaxon().parseArray(URL(url).readText())
}