package ui

import App
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import component.Avatar
import component.BottomStack
import data.QuotationOwner
import data.QuotationOwners

@Composable
fun Home(onAvatarClick: (QuotationOwner) -> Unit = {}) {
    Box(Modifier.fillMaxSize()) {
        Column {
            Header()
            BottomPanel(onAvatarClick)
        }
    }
}

@Composable
fun Header() {
    Row(
        Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton({}) {
            Icon(Icons.Outlined.Menu)
        }
        Text(
            text = App.name,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )
        IconButton({}) {
            Icon(Icons.Outlined.Settings)
        }
    }
}

@Composable
fun BottomPanel(onAvatarClick: (QuotationOwner) -> Unit = {}) {
    BottomStack {
        ScrollableColumn {
            Text(
                "People",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )

            ScrollableRow(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuotationOwners.owners.forEach {
                    Avatar(
                        ImageBitmap(100, 100, ImageBitmapConfig.Argb8888),
                        it.zhName,
                        onClick = { onAvatarClick(it) }
                    )
                }
            }

            Card(
                Modifier.fillMaxWidth().padding(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.12f)),
                elevation = 0.dp
            ) {
                Column(Modifier.clickable { }.padding(16.dp)) {
                    Text(
                        "Welcome to ${App.name}!",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.preferredHeight(8.dp))
                    Text(
                        App.version,
                        style = MaterialTheme.typography.caption
                    )
                    Spacer(Modifier.preferredHeight(8.dp))
                    TextButton({}) {
                        Text("Check for update")
                    }
                }
            }
        }
    }
}