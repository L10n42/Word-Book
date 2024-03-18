package com.kappdev.wordbook.main_feature.presentation.common.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTopAppBar(
    title: String,
    elevate: Boolean = true,
    onBack: () -> Unit
) {
    val animElevation by animateDpAsState(
        targetValue = if (elevate) 6.dp else 0.dp,
        label = "TopAppBar Elevation"
    )

    TopAppBar(
        elevation = animElevation,
        backgroundColor = MaterialTheme.colorScheme.surface,
        title = { Text(text = title) },
        navigationIcon = {
            AnimatedBackButton(onClick = onBack)
        }
    )
}