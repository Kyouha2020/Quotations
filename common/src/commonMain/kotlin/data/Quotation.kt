package data

data class QuotationIndexes(
    var block: Int = 0,
    var group: Int? = null
)

data class QuotationBlock(
    val name: String,
    val zhName: String,
    val description: String,
    val groups: List<QuotationGroup>
)

data class QuotationGroup(
    val title: String,
    val subtitle: String,
    val url: String
)

data class Quotation(
    val date: String,
    val contents: Map<String, String>,
    val tags: List<String>,
    val topics: List<String>
)

fun List<QuotationBlock>.block(indexes: QuotationIndexes): QuotationBlock? {
    return if (size > indexes.block) this[indexes.block] else null
}

fun List<QuotationBlock>.group(indexes: QuotationIndexes): QuotationGroup? {
    return indexes.group?.let { this.block(indexes)?.groups?.get(it) }
}