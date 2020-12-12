package ui

import App
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyRowFor
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
import data.Quotation
import data.QuotationGroup
import data.QuotationOwners
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import parseQuotationGroups
import parseQuotations

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Home() {
    var isLoading by remember { mutableStateOf(true) }
    var exception by remember { mutableStateOf<Exception?>(null) }
    var selectedQuotation by remember { mutableStateOf(QuotationOwners.owners[0]) }
    var quotationsVisible by remember { mutableStateOf(false) }
    var selectedGroupIndex by remember { mutableStateOf<Int?>(null) }
    val quotationGroups = remember { mutableStateListOf<QuotationGroup>() }
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
                    selectedGroupIndex = null
                }
            }) {
                Icon(
                    if (quotationsVisible) Icons.Outlined.ArrowBack
                    else Icons.Outlined.Menu
                )
            }
            Text(
                text = if (quotationsVisible) "${selectedQuotation.zhName}${
                    if (selectedGroupIndex != null) ": ${quotationGroups[selectedGroupIndex!!].title}" else ""
                }" else App.name,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            IconButton({}) {
                Icon(Icons.Outlined.Search)
            }
        }
        LaunchedEffect(selectedQuotation) {
            GlobalScope.launch(Dispatchers.Default) {
                isLoading = true
                exception = null
                try {
                    quotationGroups.clear()
                    quotationGroups += parseQuotationGroups(selectedQuotation.name)
                } catch (e: Exception) {
                    exception = e
                }
                isLoading = false
            }
        }
        LaunchedEffect(selectedGroupIndex) {
            val groupIndex = selectedGroupIndex
            if (groupIndex != null) {
                GlobalScope.launch(Dispatchers.Default) {
                    isLoading = true
                    try {
                        quotations.clear()
                        quotations += parseQuotations(quotationGroups[groupIndex].url)
                        quotations.sortBy { it.date }
                    } catch (e: Exception) {
                        exception = e
                    }
                    isLoading = false
                }
            } else if (quotationGroups.isNotEmpty()) {
                isLoading = false
            }
        }
        AnimatedVisibility(
            !quotationsVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumnForIndexed(
                quotationGroups,
                contentPadding = PaddingValues(top = 144.dp)
            ) { index, group ->
                GuidanceCard(
                    group.title,
                    group.subtitle,
                    Icons.Outlined.Https,
                    onClick = {
                        selectedGroupIndex = index
                        quotationsVisible = true
                    }
                )
            }
        }
        AnimatedVisibility(
            quotationsVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumnForIndexed(
                quotations,
                contentPadding = PaddingValues(top = 144.dp)
            ) { index, quotation ->
                QuotationCard(quotation, showDateIndexes.contains(index))
                if (!dates.contains(quotation.date)) {
                    dates += quotation.date
                    showDateIndexes += index
                }
            }
        }
        LazyRowFor(
            QuotationOwners.owners,
            contentPadding = PaddingValues(start = 8.dp, top = 56.dp, end = 8.dp)
        ) {
            Avatar(
                ImageBitmap(100, 100, ImageBitmapConfig.Argb8888),
                it.zhName,
                onClick = { selectedQuotation = it }
            )
        }
        LoadingScreen(
            isLoading,
            exception
        )
    }
}