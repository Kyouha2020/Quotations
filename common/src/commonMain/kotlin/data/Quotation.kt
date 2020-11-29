package data

interface QuotationBlock_ {
    val date: String
    val description: String
    val quotations: List<Quotation>
}

expect class QuotationBlock(
    date: String,
    description: String,
    quotations: List<Quotation>
): QuotationBlock_

interface Quotation_ {
    val content: String
    val description: String
    val tags: List<String>
}

expect class Quotation(
    content: String,
    description: String,
    tags: List<String>
): Quotation_