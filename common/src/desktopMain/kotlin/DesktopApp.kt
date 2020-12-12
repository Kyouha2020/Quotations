import data.Quotation
import java.net.URL

actual fun getPlatformName(): String = Platform.Desktop

actual fun isDarkTheme(): Boolean = false

actual fun parseQuotations(url: String): List<Quotation> {
    val data = URL(url).readText()
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