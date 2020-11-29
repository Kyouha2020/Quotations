import com.beust.klaxon.Klaxon
import data.QuotationBlock
import java.net.URL

actual fun getPlatformName(): String = Platform.Desktop

actual fun isDarkTheme(): Boolean = false

actual fun fetchQuotationBlock(url: String): List<QuotationBlock>? {
    return Klaxon().parseArray(URL(url).readText())
}