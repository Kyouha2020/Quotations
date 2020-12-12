package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun Avatar(
    image: ImageBitmap,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier.preferredHeight(88.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            image,
            modifier = Modifier.preferredSize(48.dp)
                .clip(RoundedCornerShape(50))
                .border(
                    1.dp,
                    MaterialTheme.colors.onSurface.copy(0.12f),
                    RoundedCornerShape(50)
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1
        )
    }
}