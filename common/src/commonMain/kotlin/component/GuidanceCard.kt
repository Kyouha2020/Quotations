package component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun GuidanceCard(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier.padding(16.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.08f)),
        elevation = 0.dp
    ) {
        Box(
            Modifier.fillMaxWidth()
                .preferredHeight(80.dp)
                .clickable { onClick() }
        ) {
            Icon(
                icon,
                Modifier.padding(16.dp).align(Alignment.CenterStart),
                MaterialTheme.colors.primary
            )
            Column(Modifier.padding(56.dp, 16.dp).align(Alignment.CenterStart)) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (subtitle != null) 1 else 2,
                    style = MaterialTheme.typography.subtitle2
                )
                subtitle?.let {
                    Text(
                        it,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            Icon(
                Icons.Default.KeyboardArrowRight,
                Modifier.padding(16.dp).align(Alignment.CenterEnd)
            )
        }
    }
}