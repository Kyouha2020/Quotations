package data

data class Quotation(
    val date: String,
    val contents: Map<String, String>,
    val tags: List<String>,
    val topics: List<String>
)

data class QuotationGroup(
    val title: String,
    val subtitle: String,
    val url: String
)

data class QuotationOwner(
    val name: String,
    val zhName: String,
    val description: String
)

object QuotationOwners {
    val owners = listOf(
        QuotationOwner(
            "Li Chunsheng",
            "李春生",
            "Math teacher"
        ),
        QuotationOwner(
            "Yin Jianxia",
            "殷建霞",
            "Chinese teacher"
        ),
        QuotationOwner(
            "Zhuang Changchun",
            "庄长春",
            "English teacher"
        ),
        QuotationOwner(
            "Wu Jianrong",
            "吴建荣",
            "Physics teacher"
        ),
        QuotationOwner(
            "Sun Yameng",
            "孙雅萌",
            "Biology teacher"
        ),
        QuotationOwner(
            "Liu Xuemin",
            "刘学民",
            "Geography teacher"
        )
    )
}