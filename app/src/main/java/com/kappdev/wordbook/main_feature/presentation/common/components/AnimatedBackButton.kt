package com.kappdev.wordbook.main_feature.presentation.common.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun AnimatedBackButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    var rotate by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotate) 180f else 0f,
        animationSpec = tween(400, easing = LinearEasing),
        label = "Back Button Rotation"
    )

    androidx.compose.material.IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            rotate = true
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null,
            modifier = Modifier.rotate(rotation)
        )
    }
}