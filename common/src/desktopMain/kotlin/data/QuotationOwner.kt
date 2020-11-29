package data

import java.io.Serializable

actual data class QuotationOwner actual constructor(
    override val name: String,
    override val zhName: String,
    override val description: String
): QuotationOwner_, Serializable