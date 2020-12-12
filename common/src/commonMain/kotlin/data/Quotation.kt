package data

data class Quotation(
    val date: String,
    val contents: Map<String, String>,
    val tags: List<String>,
    val topics: List<String>
)