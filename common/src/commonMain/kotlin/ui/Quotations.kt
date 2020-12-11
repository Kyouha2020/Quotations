package ui

import androidx.compose.animation.*
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import component.BottomStack
import component.LoadingScreen
import component.QuotationCard
import data.QuotationBlock
import data.QuotationOwner
import fetchQuotationBlock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Quotations(owner: QuotationOwner) {
    val quotationBlocks = remember { mutableStateListOf<QuotationBlock>() }
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var tagQuery by remember { mutableStateOf(TextFieldValue("")) }
    var exception by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var searchBarVisible by remember { mutableStateOf(false) }
    var snackbarVisible by remember { mutableStateOf(false) }

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
            IconButton({ searchBarVisible = !searchBarVisible }) {
                Icon(Icons.Outlined.Search)
            }
        }
    }

    @Composable
    fun BottomPanel() {
        BottomStack {
            LaunchedEffect(owner) {
                GlobalScope.launch(Dispatchers.Default) {
                    isLoading = true
                    quotationBlocks.clear()
                    try {
                        fetchQuotationBlock(
                            "https://quotations.surge.sh/quotations/${
                                owner.name.toLowerCase()
                                    .replace("\\s".toRegex(), "")
                            }/quotations.json"
                        )?.let {
                            quotationBlocks.addAll(it)
                        }
                    } catch (e: Exception) {
                        exception = e.toString()
                    }
                    quotationBlocks.sortByDescending { it.date }
                    isLoading = false
                }
            }
            LazyColumnFor(quotationBlocks) {
                QuotationCard(
                    it,
                    query.text,
                    tagQuery.text
                )
            }
            LoadingScreen(isLoading)
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column {
            Header()
            ScrollableColumn(
                Modifier.preferredHeight(animate(if (searchBarVisible) 128.dp else 0.dp))
                    .padding(8.dp)
                    .alpha(animate(if (searchBarVisible) 1f else 0f))
            ) {
                OutlinedTextField(
                    query,
                    { query = it },
                    Modifier.fillMaxWidth(),
                    placeholder = { Text("Search") },
                    trailingIcon = {
                        IconButton({ query = query.copy("") }) {
                            Icon(Icons.Outlined.Close)
                        }
                    }
                )
                OutlinedTextField(
                    tagQuery,
                    { tagQuery = it },
                    Modifier.fillMaxWidth(),
                    placeholder = { Text("Filter by tag") },
                    trailingIcon = {
                        IconButton({ tagQuery = tagQuery.copy("") }) {
                            Icon(Icons.Outlined.Close)
                        }
                    }
                )
            }
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