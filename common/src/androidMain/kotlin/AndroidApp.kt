import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import data.Quotation
import data.QuotationGroup
import java.net.URL
import java.util.*

actual fun getPlatformName(): String = Platform.Android

@Composable
actual fun isDarkTheme(): Boolean = isSystemInDarkTheme()

actual fun parseQuotationGroups(name: String): List<QuotationGroup> {
    val quotationGroups = mutableListOf<QuotationGroup>()
    URL(
        "https://codeberg.org/Kyouha/Quotations/raw/branch/main/data/${
            name.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), "")
        }_index.txt"
    ).readText().reader().readLines().forEach { group ->
        "\"(?<title>.*)\", \"(?<subtitle>.*)\", \"(?<url>.*)\"".toRegex().findAll(group)
            .toMutableList().map {
                quotationGroups += QuotationGroup(
                    it.groupValues[1],
                    it.groupValues[2],
                    it.groupValues[3],
                )
            }
    }
    return quotationGroups
}

actual fun parseQuotations(url: String): List<Quotation> {
    val data = URL("https://codeberg.org/Kyouha/Quotations/raw/branch/main/data/$url.txt")
        .readText()
    val quotations = mutableListOf<Quotation>()
    data.split("• ".toRegex()).forEach { block ->
        val lines = "• $block"
        quotations += Quotation(
            "• (?<date>.*)".toRegex().find(lines)?.groupValues?.get(1) ?: "0000-00-00",
            mutableMapOf<String, String>().also { contents ->
                "@(?<speaker>.*): (?<content>.*)".toRegex().findAll(lines).toMutableList().map {
                    contents += Pair(
                        if (it.groupValues[1].isNotBlank()) "${it.groupValues[1]} " else "",
                        it.groupValues[2]
                    )
                }
            },
            mutableListOf<String>().also { tags ->
                "\\$ (?<tags>.*)".toRegex().findAll(lines).toMutableList().map {
                    tags += it.groupValues[1].split(" ".toRegex())
                }
            },
            mutableListOf<String>().also { topics ->
                "# (?<topics>.*)".toRegex().findAll(lines).toMutableList().map {
                    topics += it.groupValues[1].split(" ".toRegex())
                }
            }
        )
    }
    quotations.removeAt(0)
    return quotations
}