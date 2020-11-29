package data

interface QuotationOwner_ {
    val name: String
    val zhName: String
    val description: String
}

expect class QuotationOwner(
    name: String,
    zhName: String,
    description: String
) : QuotationOwner_

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