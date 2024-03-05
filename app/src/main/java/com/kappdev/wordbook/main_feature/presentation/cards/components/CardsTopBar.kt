package com.kappdev.wordbook.main_feature.presentation.cards.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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
            ActionButton(icon = Icons.Rounded.ArrowBack, onClick = navigateBack)
        },
        actions = {
            ActionButton(icon = Icons.Rounded.Search, onClick = openSearch)
            ActionButton(icon = Icons.Rounded.Tune, onClick = openOptions)
        }
    )
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )
    }
}