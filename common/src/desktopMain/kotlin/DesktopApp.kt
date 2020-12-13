import data.Quotation
import data.QuotationBlock
import data.QuotationGroup
import java.net.URL

actual fun getPlatformName(): String = Platform.Desktop

actual fun isDarkTheme(): Boolean = false

actual fun parseQuotationBlocks(): List<QuotationBlock> {
    val quotationBlocks = mutableListOf<QuotationBlock>()
    val data =
        URL("https://codeberg.org/Kyouha/Quotations/raw/branch/main/data/index.txt").readText()
    data.split("• ".toRegex()).forEach { block ->
        val lines = "• $block"
        val info = "• \"(?<name>.*)\", \"(?<zhName>.*)\", \"(?<description>.*)\"".toRegex()
            .find(lines)?.groupValues
        val groups = mutableListOf<QuotationGroup>()
        "- \"(?<title>.*)\", \"(?<subtitle>.*)\", \"(?<url>.*)\"".toRegex().findAll(lines)
            .toMutableList().map {
                groups += QuotationGroup(
                    it.groupValues[1],
                    it.groupValues[2],
                    it.groupValues[3]
                )
            }
        info?.let {
            quotationBlocks += QuotationBlock(
                it[1],
                it[2],
                it[3],
                groups
            )
        }
    }
    return quotationBlocks
}

actual fun parseQuotations(url: String): List<Quotation> {
    val quotations = mutableListOf<Quotation>()
    val data = URL("https://codeberg.org/Kyouha/Quotations/raw/branch/main/data/$url.txt")
        .readText()
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