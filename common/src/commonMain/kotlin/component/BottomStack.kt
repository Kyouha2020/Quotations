package component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottomStack(
    content: @Composable () -> Unit
) {
    Card(
        Modifier.fillMaxSize(),
        shape = RoundedCornerShape(topLeft = 16.dp, topRight = 16.dp),
        elevation = 6.dp
    ) {
        Column {
            Divider(
                Modifier.preferredWidth(48.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50)),
                thickness = 3.dp
            )
            content()
        }
    }
}