package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.QuotationBlock
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuotationCard(
    quotationBlock: QuotationBlock,
    query: String = "",
    tagQuery: String = ""
) {
    val quotations = quotationBlock.quotations.asSequence().filter {
        if (query.isNotBlank())
            it.content.contains(query)
        else true
    }.filter {
        if (tagQuery.isNotBlank())
            it.tags.contains(tagQuery)
        else true
    }
    AnimatedVisibility(
        quotations.any(),
        enter = expandVertically(clip = true),
        exit = shrinkOut()
    ) {
        Row {
            Column(
                modifier = Modifier.preferredWidth(64.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (quotationBlock.date != "0000-00-00")
                        LocalDate.parse(quotationBlock.date).month.name.take(3).toUpperCase()
                    else "-",
                    color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                    maxLines = 1,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = if (quotationBlock.date != "0000-00-00")
                        LocalDate.parse(quotationBlock.date).dayOfMonth.toString()
                    else "-",
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    style = MaterialTheme.typography.h6
                )
            }
            Column(Modifier.padding(4.dp)) {
                if (quotationBlock.description.isNotBlank()) {
                    Card(
                        Modifier.fillMaxWidth().padding(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.12f)),
                        elevation = 0.dp
                    ) {
                        Column(Modifier.clickable { }.padding(8.dp)) {
                            Text(
                                quotationBlock.description,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
                quotationBlock.quotations.forEachIndexed { index, quotation ->
                    AnimatedVisibility(
                        quotations.contains(quotation),
                        enter = expandVertically(),
                        exit = shrinkOut()
                    ) {
                        Column {
                            Row {
                                Text(
                                    (index + 1).toString(),
                                    Modifier.padding(top = 12.dp),
                                    color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.caption
                                )
                                Column(Modifier.fillMaxWidth()
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable { }) {
                                    if (quotation.content.isNotBlank()) {
                                        Text(
                                            quotation.content,
                                            Modifier.padding(8.dp),
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                    if (quotation.description.isNotBlank()) {
                                        Text(
                                            quotation.description,
                                            Modifier.fillMaxWidth()
                                                .padding(8.dp)
                                                .background(
                                                    MaterialTheme.colors.primary.copy(0.12f),
                                                    RoundedCornerShape(8.dp)
                                                ).padding(16.dp),
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                    if (quotation.tags.isNotEmpty()) {
                                        Row(
                                            Modifier.align(Alignment.End)
                                                .padding(8.dp)
                                        ) {
                                            quotation.tags.forEach { tag ->
                                                if (tag.isNotBlank()) {
                                                    Card(
                                                        Modifier.padding(4.dp),
                                                        backgroundColor = MaterialTheme.colors.secondary,
                                                        elevation = 0.dp
                                                    ) {
                                                        Text(
                                                            tag,
                                                            Modifier.padding(horizontal = 4.dp),
                                                            style = MaterialTheme.typography.caption
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Divider(Modifier.padding(start = 12.dp, end = 4.dp, bottom = 8.dp))
                        }
                    }
                }
            }
        }
    }
}