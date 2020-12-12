package component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import data.Quotation
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalLayout::class)
@Composable
fun QuotationCard(
    quotation: Quotation,
    showDate: Boolean = true
) {
    Row {
        Column(
            Modifier.preferredWidth(64.dp)
                .alpha(if (showDate) 1f else 0f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (quotation.date != "0000-00-00")
                    LocalDate.parse(quotation.date).month.name.take(3).toUpperCase()
                else "-",
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                maxLines = 1,
                style = MaterialTheme.typography.caption
            )
            Text(
                if (quotation.date != "0000-00-00")
                    LocalDate.parse(quotation.date).dayOfMonth.toString()
                else "-",
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                style = MaterialTheme.typography.h6
            )
        }
        Column(Modifier.fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.03f))
            .clickable { }
            .padding(4.dp)) {
            if (quotation.contents.isNotEmpty()) {
                quotation.contents.entries.forEach { (speaker, content) ->
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(speaker)
                            }
                            withStyle(SpanStyle()) {
                                append(content)
                            }
                        },
                        Modifier.padding(8.dp, 2.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Box(Modifier.padding(8.dp)) {
                FlowRow(
                    mainAxisSpacing = 4.dp,
                    crossAxisSpacing = 4.dp
                ) {
                    quotation.tags.forEach {
                        if (it.isNotBlank()) {
                            Card(
                                backgroundColor = MaterialTheme.colors.secondary,
                                elevation = 0.dp
                            ) {
                                Text(
                                    it,
                                    Modifier.padding(8.dp, 2.dp),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    }
                    quotation.topics.forEach {
                        if (it.isNotBlank()) {
                            Card(
                                backgroundColor = MaterialTheme.colors.primary,
                                elevation = 0.dp
                            ) {
                                Text(
                                    it,
                                    Modifier.padding(8.dp, 2.dp),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}