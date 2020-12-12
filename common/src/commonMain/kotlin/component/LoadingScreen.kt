package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingScreen(
    isLoading: Boolean,
    exception: Exception? = null
) {
    AnimatedVisibility(
        isLoading || exception != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (exception == null) {
                CircularProgressIndicator()
            } else {
                Icon(
                    Icons.Default.Error.copy(defaultHeight = 64.dp, defaultWidth = 64.dp),
                    tint = MaterialTheme.colors.error
                )
            }
            Spacer(Modifier.preferredHeight(16.dp))
            Text(
                exception?.toString() ?: "Loading...",
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}