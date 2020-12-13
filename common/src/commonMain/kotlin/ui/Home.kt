package ui

import App
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyRowForIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Https
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import component.Avatar
import component.GuidanceCard
import component.LoadingScreen
import component.QuotationCard
import data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import parseQuotationBlocks
import parseQuotations

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Home() {
    var isLoading by remember { mutableStateOf(true) }
    var exception by remember { mutableStateOf<Exception?>(null) }
    val blocks = remember { mutableStateListOf<QuotationBlock>() }
    var indexes by remember { mutableStateOf(QuotationIndexes()) }
    var quotationsVisible by remember { mutableStateOf(false) }
    val quotations = remember { mutableStateListOf<Quotation>() }
    val dates = remember { mutableStateListOf<String>() }
    val showDateIndexes = remember { mutableStateListOf<Int>() }

    Box(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth()
                .preferredHeight(56.dp)
                .zIndex(4f)
                .background(MaterialTheme.colors.surface.copy(alpha = ContentAlpha.high))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton({
                if (quotationsVisible) {
                    exception = null
                    quotationsVisible = false
                    indexes = indexes.copy(group = null)
                }
            }) {
                Icon(
                    if (quotationsVisible) Icons.Outlined.ArrowBack
                    else Icons.Outlined.Menu
                )
            }
            Text(
                text = if (quotationsVisible) "${blocks.block(indexes)?.zhName}${
                    if (indexes.group != null) ": ${blocks.group(indexes)?.title}" else ""
                }" else App.name,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            IconButton({}) {
                Icon(Icons.Outlined.Search)
            }
        }
        LaunchedEffect("") {
            GlobalScope.launch(Dispatchers.Default) {
                isLoading = true
                exception = null
                try {
                    blocks.clear()
                    blocks += parseQuotationBlocks()
                } catch (e: Exception) {
                    println(e)
                    exception = e
                }
                isLoading = false
            }
        }
        LaunchedEffect(indexes.group) {
            val group = blocks.group(indexes)
            if (group != null) {
                GlobalScope.launch(Dispatchers.Default) {
                    isLoading = true
                    try {
                        quotations.clear()
                        quotations += parseQuotations(group.url)
                        quotations.sortBy { it.date }
                    } catch (e: Exception) {
                        println(e)
                        exception = e
                    }
                    isLoading = false
                }
            } else if (blocks.isNotEmpty()) {
                isLoading = false
            }
        }
        AnimatedVisibility(
            !quotationsVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            blocks.block(indexes)?.let {
                LazyColumnForIndexed(
                    it.groups,
                    contentPadding = PaddingValues(top = 144.dp)
                ) { index, group ->
                    GuidanceCard(
                        group.title,
                        group.subtitle,
                        Icons.Outlined.Https,
                        onClick = {
                            indexes = indexes.copy(group = index)
                            quotationsVisible = true
                        }
                    )
                }
            }
        }
        AnimatedVisibility(
            quotationsVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumnForIndexed(
                quotations,
                contentPadding = PaddingValues(top = 56.dp)
            ) { index, quotation ->
                QuotationCard(quotation, showDateIndexes.contains(index))
                if (!dates.contains(quotation.date)) {
                    dates += quotation.date
                    showDateIndexes += index
                }
            }
        }
        LoadingScreen(
            isLoading,
            exception
        )
        AnimatedVisibility(
            indexes.group == null,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            LazyRowForIndexed(
                blocks,
                contentPadding = PaddingValues(start = 8.dp, top = 56.dp, end = 8.dp)
            ) { index, block ->
                Avatar(
                    ImageBitmap(100, 100, ImageBitmapConfig.Argb8888),
                    block.zhName,
                    indexes.block == index,
                    onClick = { indexes = indexes.copy(block = index) }
                )
            }
        }
    }
}