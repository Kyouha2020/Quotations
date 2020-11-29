package data

import com.beust.klaxon.Json

actual data class QuotationBlock actual constructor(
    @Json("date")
    override val date: String,
    @Json("desc")
    override val description: String,
    @Json("quotations")
    override val quotations: List<Quotation>
) : QuotationBlock_

actual data class Quotation actual constructor(
    @Json("content")
    override val content: String,
    @Json("desc")
    override val description: String,
    @Json("tags")
    override val tags: List<String>
) : Quotation_