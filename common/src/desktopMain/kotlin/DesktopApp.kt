import data.Quotation
import java.net.URL

actual fun getPlatformName(): String = Platform.Desktop

actual fun isDarkTheme(): Boolean = false

actual fun parseQuotations(url: String): List<Quotation> {
    val data = URL(url).readText()
    val quotations = mutableListOf<Quotation>()
    data.split("• ".toRegex()).forEach {
        val lines = "• $it"
        quotations += Quotation(
            "• (?<date>.*)".toRegex().find(lines)?.groupValues?.get(1) ?: "0000-00-00",
            mutableMapOf<String, String>().also { map ->
                "@(?<speaker>.*): (?<content>.*)".toRegex().findAll(lines).toMutableList().map {
                    map += Pair(
                        if (it.groupValues[1].isNotBlank()) "${it.groupValues[1]} " else "",
                        it.groupValues[2]
                    )
                }
            },
            "\\$ (?<tags>.*)".toRegex().find(lines)?.groupValues?.get(1)
                ?.split(" ".toRegex()) ?: emptyList(),
            "# (?<topics>.*)".toRegex().find(lines)?.groupValues?.get(1)
                ?.split(" ".toRegex()) ?: emptyList()
        )
    }
    quotations.removeAt(0)
    return quotations
}