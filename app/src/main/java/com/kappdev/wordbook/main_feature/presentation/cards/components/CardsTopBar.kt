package com.kappdev.wordbook.main_feature.presentation.cards.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.main_feature.presentation.common.IconButton

@Composable
fun CardsTopBar(
    collectionName: String,
    navigateBack: () -> Unit,
    openOptions: () -> Unit,
    openSearch: () -> Unit
) {
    TopAppBar(
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colorScheme.surface,
        title = { Text(text = collectionName) },
        navigationIcon = {
            IconButton(icon = Icons.Rounded.ArrowBack, onClick = navigateBack)
        },
        actions = {
            IconButton(icon = Icons.Rounded.Search, onClick = openSearch)
            IconButton(icon = Icons.Rounded.Tune, onClick = openOptions)
        }
    )
}

