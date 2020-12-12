package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import component.BottomStack
import component.LoadingScreen
import component.QuotationCard
import data.Quotation
import data.QuotationOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import parseQuotations

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Quotations(owner: QuotationOwner) {
    var exception by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

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
                text = owner.zhName,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            IconButton({}) {
                Icon(Icons.Outlined.Search)
            }
        }
    }

    @Composable
    fun BottomPanel() {
        BottomStack {
            val quotations = remember { mutableStateListOf<Quotation>() }
            val dates = remember { mutableStateListOf<String>() }
            val showDateIndexes = remember { mutableStateListOf<Int>() }
            LaunchedEffect(owner) {
                GlobalScope.launch(Dispatchers.Default) {
                    isLoading = true
                    try {
                        quotations += parseQuotations(owner.name)
                    } catch (e: Exception) {
                        exception = e.toString()
                    }
                    quotations.sortByDescending { it.date }
                    isLoading = false
                }
            }
            LazyColumnForIndexed(quotations) { index, quotation ->
                QuotationCard(quotation, showDateIndexes.contains(index))
                if (!dates.contains(quotation.date)) {
                    dates += quotation.date
                    showDateIndexes += index
                }
            }
            LoadingScreen(isLoading)
        }
    }

    Box(Modifier.fillMaxSize()) {
        var snackbarVisible by remember { mutableStateOf(false) }
        Column {
            Header()
            BottomPanel()
        }
        onCommit(exception) {
            if (exception.isNotBlank()) {
                snackbarVisible = true
                GlobalScope.launch(Dispatchers.Default) {
                    delay(2500L)
                    snackbarVisible = false
                }
            }
        }
        AnimatedVisibility(
            visible = snackbarVisible,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            enter = slideInHorizontally(),
            exit = fadeOut(),
        ) {
            Snackbar {
                Text(exception)
            }
        }
    }
}